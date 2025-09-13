#!/bin/bash

# Astropa Setup Verification Script
echo "🌟 Astropa Project Setup Verification"
echo "======================================"

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

success_count=0
total_checks=0

check_command() {
    local cmd="$1"
    local name="$2"
    total_checks=$((total_checks + 1))
    
    if command -v "$cmd" &> /dev/null; then
        echo -e "✅ $name is installed"
        success_count=$((success_count + 1))
    else
        echo -e "❌ $name is NOT installed"
    fi
}

check_file() {
    local file="$1"
    local name="$2"
    total_checks=$((total_checks + 1))
    
    if [ -f "$file" ]; then
        echo -e "✅ $name exists"
        success_count=$((success_count + 1))
    else
        echo -e "❌ $name is missing"
    fi
}

echo ""
echo "🔍 Checking Prerequisites..."
echo "----------------------------"
check_command "java" "Java"
check_command "node" "Node.js"
check_command "npm" "NPM"
check_command "docker" "Docker"
check_command "git" "Git"

echo ""
echo "📁 Checking Project Structure..."
echo "--------------------------------"
check_file "package.json" "Root package.json"
check_file ".husky/pre-commit" "Husky pre-commit hook"
check_file "backend/pom.xml" "Backend POM"
check_file "frontend/package.json" "Frontend package.json"
check_file "docker-compose.yml" "Docker Compose"
check_file ".github/workflows/ci-cd.yml" "GitHub Actions workflow"

echo ""
echo "🐳 Checking Docker files..."
echo "---------------------------"
check_file "backend/Dockerfile" "Backend Dockerfile"
check_file "frontend/Dockerfile" "Frontend Dockerfile"
check_file "frontend/nginx.conf" "Frontend Nginx config"

echo ""
echo "☸️ Checking Kubernetes manifests..."
echo "-----------------------------------"
check_file "k8s/base/namespace.yaml" "Namespace config"
check_file "k8s/base/backend-deployment.yaml" "Backend deployment"
check_file "k8s/base/frontend-deployment.yaml" "Frontend deployment"
check_file "k8s/base/database-statefulset.yaml" "Database StatefulSet"
check_file "k8s/base/ingress.yaml" "Ingress config"

echo ""
echo "📚 Checking Documentation..."
echo "----------------------------"
check_file "README.md" "README"
check_file "CONTRIBUTING.md" "Contributing Guide"
check_file "docs/DEPLOYMENT.md" "Deployment Guide"
check_file ".env.example" "Environment template"

echo ""
echo "🧪 Running quick tests..."
echo "-------------------------"

# Test backend build
echo "Testing backend build..."
total_checks=$((total_checks + 1))
if cd backend && ./mvnw compile -q; then
    echo -e "✅ Backend compiles successfully"
    success_count=$((success_count + 1))
    cd ..
else
    echo -e "❌ Backend compilation failed"
    cd ..
fi

# Test frontend build
echo "Testing frontend build..."
total_checks=$((total_checks + 1))
if cd frontend && npm run build --silent > /dev/null 2>&1; then
    echo -e "✅ Frontend builds successfully"
    success_count=$((success_count + 1))
    cd ..
else
    echo -e "❌ Frontend build failed"
    cd ..
fi

# Test Docker Compose syntax
echo "Testing Docker Compose..."
total_checks=$((total_checks + 1))
if docker-compose config > /dev/null 2>&1; then
    echo -e "✅ Docker Compose configuration is valid"
    success_count=$((success_count + 1))
else
    echo -e "❌ Docker Compose configuration has errors"
fi

echo ""
echo "📊 Summary"
echo "=========="
echo -e "Passed: ${GREEN}$success_count${NC}/$total_checks checks"

if [ $success_count -eq $total_checks ]; then
    echo -e "${GREEN}🎉 All checks passed! Your Astropa setup is ready!${NC}"
    echo ""
    echo "Next steps:"
    echo "1. Update .env file with your configuration"
    echo "2. Run: npm run docker:up"
    echo "3. Open http://localhost:4200"
    exit 0
else
    failed=$((total_checks - success_count))
    echo -e "${YELLOW}⚠️  $failed checks failed. Please review the setup.${NC}"
    exit 1
fi