# 🚀 Astropa Deployment Guide

This comprehensive guide covers all deployment scenarios for the Astropa application, from local development to production-grade Kubernetes clusters.

## Table of Contents
- [Quick Start](#quick-start)
- [Environment Setup](#environment-setup)
- [Local Development](#local-development)
- [Testing Environment](#testing-environment)
- [Staging Deployment](#staging-deployment)
- [Production Deployment](#production-deployment)
- [Monitoring & Observability](#monitoring--observability)
- [Security Considerations](#security-considerations)
- [Troubleshooting](#troubleshooting)

## Quick Start

### Prerequisites
- **Docker** 20.10+
- **Docker Compose** 2.0+
- **kubectl** 1.25+
- **Helm** 3.8+
- **Java** 21+
- **Node.js** 20+

### 30-Second Setup
```bash
# Clone and start
git clone https://github.com/yourusername/astropa-2.git
cd astropa-2
cp .env.example .env
docker-compose up -d

# Access application
open http://localhost:4200
```

## Environment Setup

### Environment Variables

Create `.env` file from template:
```bash
cp .env.example .env
```

Edit the following critical variables:
```bash
# Security (CHANGE IN PRODUCTION!)
JWT_SECRET=your-super-secret-jwt-key-here-make-it-long-and-random

# Database
DB_PASSWORD=your-secure-database-password

# External services
GITHUB_CLIENT_ID=your-github-oauth-app-id
GITHUB_CLIENT_SECRET=your-github-oauth-secret
```

### SSL/TLS Certificates

For production deployments:
```bash
# Using cert-manager with Let's Encrypt
kubectl apply -f https://github.com/jetstack/cert-manager/releases/download/v1.13.0/cert-manager.yaml

# Install cluster issuer
kubectl apply -f k8s/base/ingress.yaml
```

## Local Development

### Docker Compose (Recommended)

**Start all services:**
```bash
docker-compose up -d
```

**View logs:**
```bash
docker-compose logs -f
```

**Stop services:**
```bash
docker-compose down
```

**Clean rebuild:**
```bash
docker-compose down -v
docker-compose build --no-cache
docker-compose up -d
```

### Manual Development Setup

**Backend:**
```bash
cd backend
./mvnw spring-boot:run -Dspring.profiles.active=local
```

**Frontend:**
```bash
cd frontend
npm install
npm start
```

**Database:**
```bash
docker run -d --name astropa-db \
  -e MYSQL_ROOT_PASSWORD=rootpassword \
  -e MYSQL_DATABASE=astropa \
  -e MYSQL_USER=astropa \
  -e MYSQL_PASSWORD=astropapass \
  -p 3306:3306 \
  mariadb:10.11
```

### Development URLs
- **Frontend**: http://localhost:4200
- **Backend API**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **H2 Console**: http://localhost:8080/h2-console
- **Actuator Health**: http://localhost:8080/actuator/health

## Testing Environment

### Integration Testing
```bash
# Start test environment
docker-compose -f docker-compose.test.yml up -d

# Run backend tests
cd backend
mvn clean verify -Pintegration-test

# Run frontend tests
cd frontend
npm run test:ci
npm run e2e

# Cleanup
docker-compose -f docker-compose.test.yml down
```

### Performance Testing
```bash
# Using Artillery.js
npm install -g artillery
artillery run performance-tests/load-test.yml
```

### Security Testing
```bash
# OWASP ZAP baseline scan
docker run -t owasp/zap2docker-stable zap-baseline.py \
  -t http://localhost:4200
```

## Staging Deployment

### Prerequisites
- Kubernetes cluster (1.25+)
- kubectl configured
- Ingress controller installed
- cert-manager installed

### Deploy to Staging

1. **Create namespace and secrets:**
```bash
kubectl apply -f k8s/base/namespace.yaml

# Create secrets (replace with actual values)
kubectl create secret generic astropa-secrets \
  --namespace=astropa \
  --from-literal=database-username=astropa \
  --from-literal=database-password=STAGING_DB_PASSWORD \
  --from-literal=jwt-secret=STAGING_JWT_SECRET
```

2. **Deploy database:**
```bash
kubectl apply -f k8s/base/database-statefulset.yaml
kubectl rollout status statefulset/astropa-database -n astropa
```

3. **Deploy applications:**
```bash
# Backend
kubectl apply -f k8s/base/configmap.yaml
kubectl apply -f k8s/base/backend-deployment.yaml
kubectl rollout status deployment/astropa-backend -n astropa

# Frontend (create missing deployment first)
kubectl apply -f k8s/base/frontend-deployment.yaml
kubectl rollout status deployment/astropa-frontend -n astropa
```

4. **Setup ingress:**
```bash
kubectl apply -f k8s/base/ingress.yaml
```

5. **Verify deployment:**
```bash
kubectl get pods -n astropa
kubectl get services -n astropa
kubectl get ingress -n astropa
```

### Staging Environment URLs
- **Application**: https://staging.astropa.yourdomain.com
- **API Docs**: https://staging.astropa.yourdomain.com/swagger-ui.html

## Production Deployment

### Pre-deployment Checklist
- [ ] Security secrets updated
- [ ] Database backed up
- [ ] Monitoring configured
- [ ] SSL certificates valid
- [ ] Load testing completed
- [ ] Security scan passed
- [ ] Blue-green deployment ready

### Production Deployment Steps

1. **Backup existing data:**
```bash
kubectl exec -n astropa astropa-database-0 -- \
  mysqldump -u root -p$DB_PASSWORD astropa > backup-$(date +%Y%m%d).sql
```

2. **Deploy using blue-green strategy:**
```bash
# Tag current deployment as blue
kubectl patch deployment astropa-backend -n astropa \
  -p '{"spec":{"template":{"metadata":{"labels":{"version":"blue"}}}}}'

# Deploy green version
kubectl set image deployment/astropa-backend -n astropa \
  backend=ghcr.io/yourusername/astropa-2/backend:v1.2.0

# Wait for rollout
kubectl rollout status deployment/astropa-backend -n astropa

# Run smoke tests
kubectl exec -n astropa deployment/astropa-backend -- \
  curl -f http://localhost:8080/actuator/health
```

3. **Switch traffic to green:**
```bash
# Update service selector
kubectl patch service astropa-backend -n astropa \
  -p '{"spec":{"selector":{"version":"green"}}}'
```

4. **Verify and cleanup:**
```bash
# Monitor for 10 minutes
kubectl logs -f deployment/astropa-backend -n astropa

# If successful, cleanup blue deployment
kubectl delete deployment astropa-backend-blue -n astropa
```

### Production Environment URLs
- **Application**: https://astropa.yourdomain.com
- **Admin Dashboard**: https://admin.astropa.yourdomain.com
- **Monitoring**: https://grafana.yourdomain.com

## Monitoring & Observability

### Setup Prometheus & Grafana

1. **Install monitoring stack:**
```bash
helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
helm repo add grafana https://grafana.github.io/helm-charts
helm repo update

# Install Prometheus
helm install prometheus prometheus-community/kube-prometheus-stack \
  --namespace monitoring \
  --create-namespace

# Install Grafana
helm install grafana grafana/grafana \
  --namespace monitoring \
  --set persistence.enabled=true \
  --set persistence.size=10Gi
```

2. **Deploy application monitoring:**
```bash
kubectl apply -f k8s/monitoring/prometheus.yaml
```

3. **Access dashboards:**
```bash
# Grafana password
kubectl get secret --namespace monitoring grafana -o jsonpath="{.data.admin-password}" | base64 --decode

# Port forward to access locally
kubectl port-forward -n monitoring svc/grafana 3000:80
```

### Application Metrics
- **Availability SLO**: 99.9% uptime
- **Latency SLO**: 95% of requests < 500ms
- **Error Rate SLO**: < 1% error rate
- **Throughput**: Monitor requests/second

### Health Checks
```bash
# Backend health
curl https://astropa.yourdomain.com/actuator/health

# Frontend health
curl https://astropa.yourdomain.com/health

# Database connectivity
kubectl exec -n astropa astropa-database-0 -- \
  mysqladmin ping -h localhost
```

## Security Considerations

### Network Security
```bash
# Apply network policies
kubectl apply -f k8s/security/security-policies.yaml

# Verify network isolation
kubectl exec -n astropa astropa-backend-xxx -- \
  nc -zv external-service.com 443
```

### Image Security
```bash
# Scan images before deployment
trivy image ghcr.io/yourusername/astropa-2/backend:latest

# Verify signatures
cosign verify ghcr.io/yourusername/astropa-2/backend:latest
```

### Secret Management
```bash
# Rotate JWT secrets
kubectl patch secret astropa-secrets -n astropa \
  --type='json' \
  -p='[{"op": "replace", "path": "/data/jwt-secret", "value": "NEW_JWT_SECRET_BASE64"}]'

# Restart deployments to pick up new secrets
kubectl rollout restart deployment/astropa-backend -n astropa
```

### Compliance Scanning
```bash
# CIS Kubernetes benchmark
kube-bench run --targets node,policies,managedservices

# Pod security standards
kubectl apply -f - <<EOF
apiVersion: v1
kind: Namespace
metadata:
  name: astropa
  labels:
    pod-security.kubernetes.io/enforce: restricted
    pod-security.kubernetes.io/audit: restricted
    pod-security.kubernetes.io/warn: restricted
EOF
```

## Troubleshooting

### Common Issues

#### 1. Database Connection Issues
```bash
# Check database pod status
kubectl get pods -n astropa -l component=database

# Check database logs
kubectl logs -n astropa astropa-database-0

# Test connectivity from backend
kubectl exec -n astropa deployment/astropa-backend -- \
  nc -zv astropa-database 3306
```

#### 2. High Memory Usage
```bash
# Check memory usage
kubectl top pods -n astropa

# Analyze heap dump
kubectl exec -n astropa astropa-backend-xxx -- \
  jcmd 1 GC.run_finalization
```

#### 3. SSL Certificate Issues
```bash
# Check certificate status
kubectl describe certificate astropa-tls -n astropa

# Check cert-manager logs
kubectl logs -n cert-manager deployment/cert-manager
```

#### 4. Performance Issues
```bash
# Check application metrics
kubectl port-forward -n astropa svc/astropa-backend 8080:8080
curl http://localhost:8080/actuator/metrics

# Profile application
kubectl exec -n astropa astropa-backend-xxx -- \
  java -XX:+FlightRecorder -XX:StartFlightRecording=duration=60s,filename=profile.jfr
```

### Debug Commands
```bash
# Get all resources
kubectl get all -n astropa

# Describe failing pod
kubectl describe pod <pod-name> -n astropa

# Check events
kubectl get events -n astropa --sort-by='.lastTimestamp'

# Access pod shell
kubectl exec -it <pod-name> -n astropa -- /bin/bash

# Port forward for debugging
kubectl port-forward -n astropa svc/astropa-backend 8080:8080
```

### Log Analysis
```bash
# Tail application logs
kubectl logs -f deployment/astropa-backend -n astropa

# Search for errors
kubectl logs deployment/astropa-backend -n astropa | grep ERROR

# Export logs for analysis
kubectl logs deployment/astropa-backend -n astropa --since=1h > app.log
```

## Recovery Procedures

### Database Recovery
```bash
# Restore from backup
kubectl exec -i -n astropa astropa-database-0 -- \
  mysql -u root -p$DB_PASSWORD astropa < backup-20240115.sql
```

### Application Recovery
```bash
# Rollback deployment
kubectl rollout undo deployment/astropa-backend -n astropa

# Scale up replicas
kubectl scale deployment astropa-backend --replicas=5 -n astropa
```

### Disaster Recovery
```bash
# Backup cluster state
kubectl get all --all-namespaces -o yaml > cluster-backup.yaml

# Restore cluster
kubectl apply -f cluster-backup.yaml
```

---

## 🔧 Automation Scripts

### Deployment Script
```bash
#!/bin/bash
# deploy.sh - Automated deployment script

set -e

ENVIRONMENT=${1:-staging}
VERSION=${2:-latest}

echo "Deploying Astropa v$VERSION to $ENVIRONMENT"

# Update image tags
kubectl set image deployment/astropa-backend -n astropa \
  backend=ghcr.io/yourusername/astropa-2/backend:$VERSION

kubectl set image deployment/astropa-frontend -n astropa \
  frontend=ghcr.io/yourusername/astropa-2/frontend:$VERSION

# Wait for rollout
kubectl rollout status deployment/astropa-backend -n astropa
kubectl rollout status deployment/astropa-frontend -n astropa

# Run smoke tests
./scripts/smoke-test.sh $ENVIRONMENT

echo "Deployment successful!"
```

### Health Check Script
```bash
#!/bin/bash
# health-check.sh - Comprehensive health check

BACKEND_URL=${1:-http://localhost:8080}
FRONTEND_URL=${2:-http://localhost:4200}

# Backend health
if curl -f $BACKEND_URL/actuator/health; then
  echo "✅ Backend healthy"
else
  echo "❌ Backend unhealthy"
  exit 1
fi

# Frontend health
if curl -f $FRONTEND_URL/health; then
  echo "✅ Frontend healthy"
else
  echo "❌ Frontend unhealthy"
  exit 1
fi

echo "🎉 All services healthy!"
```

For additional help, see:
- [Architecture Documentation](./docs/ARCHITECTURE.md)
- [API Documentation](http://localhost:8080/swagger-ui.html)
- [Contributing Guide](./CONTRIBUTING.md)
- [Security Policy](./docs/SECURITY.md)