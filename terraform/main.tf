# Terraform configuration for Astropa infrastructure
terraform {
  required_version = ">= 1.0"
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }
    kubernetes = {
      source  = "hashicorp/kubernetes"
      version = "~> 2.23"
    }
    helm = {
      source  = "hashicorp/helm"
      version = "~> 2.11"
    }
  }

  backend "s3" {
    # Configure S3 backend for state management
    # bucket = "astropa-terraform-state"
    # key    = "production/terraform.tfstate"
    # region = "us-west-2"
    # encrypt = true
    # dynamodb_table = "astropa-terraform-locks"
  }
}

# Provider configuration
provider "aws" {
  region = var.aws_region
  
  default_tags {
    tags = {
      Project     = "Astropa"
      Environment = var.environment
      ManagedBy   = "Terraform"
    }
  }
}

# Local values
locals {
  cluster_name = "${var.project_name}-${var.environment}"
  
  common_tags = {
    Project     = var.project_name
    Environment = var.environment
    ManagedBy   = "Terraform"
  }
}

# VPC Configuration
module "vpc" {
  source = "terraform-aws-modules/vpc/aws"
  version = "~> 5.0"

  name = "${local.cluster_name}-vpc"
  cidr = var.vpc_cidr

  azs             = data.aws_availability_zones.available.names
  private_subnets = var.private_subnets
  public_subnets  = var.public_subnets

  enable_nat_gateway = true
  enable_vpn_gateway = false
  enable_dns_hostnames = true
  enable_dns_support = true

  public_subnet_tags = {
    "kubernetes.io/role/elb" = "1"
    "kubernetes.io/cluster/${local.cluster_name}" = "owned"
  }

  private_subnet_tags = {
    "kubernetes.io/role/internal-elb" = "1"
    "kubernetes.io/cluster/${local.cluster_name}" = "owned"
  }

  tags = local.common_tags
}

# EKS Cluster
module "eks" {
  source = "terraform-aws-modules/eks/aws"
  version = "~> 19.0"

  cluster_name    = local.cluster_name
  cluster_version = var.kubernetes_version

  vpc_id                         = module.vpc.vpc_id
  subnet_ids                     = module.vpc.private_subnets
  cluster_endpoint_public_access = true
  cluster_endpoint_private_access = true

  # Encryption
  cluster_encryption_config = {
    provider_key_arn = aws_kms_key.eks.arn
    resources        = ["secrets"]
  }

  # EKS Managed Node Groups
  eks_managed_node_groups = {
    main = {
      name = "${local.cluster_name}-nodes"
      
      instance_types = var.node_instance_types
      capacity_type  = "ON_DEMAND"
      
      min_size     = var.node_group_min_size
      max_size     = var.node_group_max_size
      desired_size = var.node_group_desired_size

      # Use latest EKS Optimized AMI
      ami_type = "AL2_x86_64"

      # Node security
      create_security_group = true
      
      # Node labels and taints
      labels = {
        Environment = var.environment
        NodeGroup   = "main"
      }

      tags = local.common_tags
    }
  }

  # Fargate profiles for serverless workloads
  fargate_profiles = {
    default = {
      name = "${local.cluster_name}-fargate"
      selectors = [
        {
          namespace = "astropa"
          labels = {
            compute-type = "fargate"
          }
        }
      ]
      
      tags = local.common_tags
    }
  }

  tags = local.common_tags
}

# RDS for production database
module "rds" {
  source = "terraform-aws-modules/rds/aws"
  version = "~> 6.0"

  identifier = "${local.cluster_name}-db"

  # Database configuration
  engine            = "mariadb"
  engine_version    = "10.11.4"
  instance_class    = var.rds_instance_class
  allocated_storage = var.rds_allocated_storage
  storage_type      = "gp3"
  storage_encrypted = true

  # Database settings
  db_name  = "astropa"
  username = "astropa"
  port     = "3306"

  # Networking
  vpc_id               = module.vpc.vpc_id
  db_subnet_group_name = module.vpc.database_subnet_group
  create_db_subnet_group = false

  # Security
  create_db_security_group = true
  security_group_rules = {
    mysql_ingress = {
      type                     = "ingress"
      from_port                = 3306
      to_port                  = 3306
      protocol                 = "tcp"
      source_security_group_id = module.eks.node_security_group_id
    }
  }

  # Backup and maintenance
  backup_retention_period = 7
  backup_window          = "03:00-04:00"
  maintenance_window     = "sun:04:00-sun:05:00"

  # Monitoring
  monitoring_interval = 60
  monitoring_role_name = "${local.cluster_name}-rds-monitoring"
  create_monitoring_role = true

  tags = local.common_tags
}

# ElastiCache Redis for caching
module "redis" {
  source = "terraform-aws-modules/elasticache/aws"
  version = "~> 1.2"

  cluster_id               = "${local.cluster_name}-redis"
  description             = "Redis cluster for Astropa caching"
  
  node_type               = var.redis_node_type
  num_cache_nodes         = 1
  parameter_group_name    = "default.redis7"
  
  port                    = 6379
  subnet_group_name       = module.vpc.elasticache_subnet_group_name
  security_group_ids      = [aws_security_group.redis.id]

  tags = local.common_tags
}

# Application Load Balancer
resource "aws_lb" "main" {
  name               = "${local.cluster_name}-alb"
  internal           = false
  load_balancer_type = "application"
  security_groups    = [aws_security_group.alb.id]
  subnets            = module.vpc.public_subnets

  enable_deletion_protection = var.environment == "production"

  tags = local.common_tags
}

# Data sources
data "aws_availability_zones" "available" {
  state = "available"
}

data "aws_caller_identity" "current" {}

# Outputs
output "cluster_endpoint" {
  description = "Endpoint for EKS control plane"
  value       = module.eks.cluster_endpoint
}

output "cluster_name" {
  description = "Kubernetes Cluster Name"
  value       = module.eks.cluster_name
}

output "cluster_certificate_authority_data" {
  description = "Base64 encoded certificate data required to communicate with the cluster"
  value       = module.eks.cluster_certificate_authority_data
}

output "rds_endpoint" {
  description = "RDS instance endpoint"
  value       = module.rds.db_instance_endpoint
  sensitive   = true
}

output "redis_endpoint" {
  description = "Redis cluster endpoint"
  value       = module.redis.cluster_address
}

output "alb_dns_name" {
  description = "DNS name of the load balancer"
  value       = aws_lb.main.dns_name
}