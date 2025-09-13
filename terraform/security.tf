# Security resources for Astropa infrastructure

# KMS Key for EKS encryption
resource "aws_kms_key" "eks" {
  description             = "${local.cluster_name} EKS Secret Encryption"
  deletion_window_in_days = 7
  enable_key_rotation     = true

  tags = merge(local.common_tags, {
    Name = "${local.cluster_name}-eks-encryption"
  })
}

resource "aws_kms_alias" "eks" {
  name          = "alias/${local.cluster_name}-eks"
  target_key_id = aws_kms_key.eks.key_id
}

# Security Group for ALB
resource "aws_security_group" "alb" {
  name        = "${local.cluster_name}-alb"
  description = "Security group for Application Load Balancer"
  vpc_id      = module.vpc.vpc_id

  ingress {
    description = "HTTP"
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = var.allowed_cidr_blocks
  }

  ingress {
    description = "HTTPS"
    from_port   = 443
    to_port     = 443
    protocol    = "tcp"
    cidr_blocks = var.allowed_cidr_blocks
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = merge(local.common_tags, {
    Name = "${local.cluster_name}-alb"
  })
}

# Security Group for Redis
resource "aws_security_group" "redis" {
  name        = "${local.cluster_name}-redis"
  description = "Security group for Redis cluster"
  vpc_id      = module.vpc.vpc_id

  ingress {
    description     = "Redis"
    from_port       = 6379
    to_port         = 6379
    protocol        = "tcp"
    security_groups = [module.eks.node_security_group_id]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = merge(local.common_tags, {
    Name = "${local.cluster_name}-redis"
  })
}

# IAM Role for Application Pods
resource "aws_iam_role" "pod_execution_role" {
  name = "${local.cluster_name}-pod-execution-role"

  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Action = "sts:AssumeRole"
        Effect = "Allow"
        Principal = {
          Service = "eks.amazonaws.com"
        }
      },
      {
        Action = "sts:AssumeRoleWithWebIdentity"
        Effect = "Allow"
        Principal = {
          Federated = module.eks.oidc_provider_arn
        }
        Condition = {
          StringEquals = {
            "${replace(module.eks.cluster_oidc_issuer_url, "https://", "")}:sub" = "system:serviceaccount:astropa:astropa-service-account"
            "${replace(module.eks.cluster_oidc_issuer_url, "https://", "")}:aud" = "sts.amazonaws.com"
          }
        }
      }
    ]
  })

  tags = local.common_tags
}

# IAM Policy for pod access to AWS services
resource "aws_iam_policy" "pod_policy" {
  name        = "${local.cluster_name}-pod-policy"
  description = "IAM policy for application pods"

  policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Effect = "Allow"
        Action = [
          "s3:GetObject",
          "s3:PutObject",
          "s3:DeleteObject"
        ]
        Resource = [
          "arn:aws:s3:::${local.cluster_name}-storage/*"
        ]
      },
      {
        Effect = "Allow"
        Action = [
          "secretsmanager:GetSecretValue",
          "secretsmanager:DescribeSecret"
        ]
        Resource = [
          "arn:aws:secretsmanager:${var.aws_region}:${data.aws_caller_identity.current.account_id}:secret:${local.cluster_name}/*"
        ]
      },
      {
        Effect = "Allow"
        Action = [
          "ssm:GetParameter",
          "ssm:GetParameters",
          "ssm:GetParametersByPath"
        ]
        Resource = [
          "arn:aws:ssm:${var.aws_region}:${data.aws_caller_identity.current.account_id}:parameter/${local.cluster_name}/*"
        ]
      }
    ]
  })
}

resource "aws_iam_role_policy_attachment" "pod_policy" {
  role       = aws_iam_role.pod_execution_role.name
  policy_arn = aws_iam_policy.pod_policy.arn
}

# S3 Bucket for application storage
resource "aws_s3_bucket" "storage" {
  bucket = "${local.cluster_name}-storage"

  tags = local.common_tags
}

resource "aws_s3_bucket_versioning" "storage" {
  bucket = aws_s3_bucket.storage.id
  versioning_configuration {
    status = "Enabled"
  }
}

resource "aws_s3_bucket_encryption" "storage" {
  bucket = aws_s3_bucket.storage.id

  server_side_encryption_configuration {
    rule {
      apply_server_side_encryption_by_default {
        sse_algorithm = "AES256"
      }
    }
  }
}

resource "aws_s3_bucket_public_access_block" "storage" {
  bucket = aws_s3_bucket.storage.id

  block_public_acls       = true
  block_public_policy     = true
  ignore_public_acls      = true
  restrict_public_buckets = true
}

# Secrets Manager secret for database credentials
resource "aws_secretsmanager_secret" "db_credentials" {
  name        = "${local.cluster_name}/db-credentials"
  description = "Database credentials for Astropa"

  tags = local.common_tags
}

resource "aws_secretsmanager_secret_version" "db_credentials" {
  secret_id = aws_secretsmanager_secret.db_credentials.id
  secret_string = jsonencode({
    username = "astropa"
    password = random_password.db_password.result
    engine   = "mariadb"
    host     = module.rds.db_instance_endpoint
    port     = 3306
    dbname   = "astropa"
  })
}

resource "random_password" "db_password" {
  length  = 32
  special = true
}

# SSM Parameters for application configuration
resource "aws_ssm_parameter" "app_config" {
  for_each = {
    "/astropa/environment"     = var.environment
    "/astropa/region"         = var.aws_region
    "/astropa/cluster-name"   = local.cluster_name
    "/astropa/redis-endpoint" = module.redis.cluster_address
  }

  name  = "${local.cluster_name}${each.key}"
  type  = "String"
  value = each.value

  tags = local.common_tags
}