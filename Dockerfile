# Frontend Dockerfile - Multi-stage build for optimization
FROM node:20-alpine AS builder

WORKDIR /app

# Copy frontend package files first to leverage Docker cache layers
COPY frontend/package*.json ./

# Install dependencies
RUN npm install

# Copy frontend source code
COPY frontend/ .

# Build the application for production
RUN npm run build:prod

# Production stage with nginx
FROM nginx:1.25-alpine

# Install curl for health checks
RUN apk add --no-cache curl

# Copy custom nginx configuration
COPY frontend/nginx.conf /etc/nginx/nginx.conf

# Copy built application from builder stage
COPY --from=builder /app/dist/astropa /usr/share/nginx/html

# Create non-root user for security
RUN addgroup -g 1001 -S astropa && \
    adduser -S astropa -u 1001 -G astropa

# Change ownership of nginx directories
RUN chown -R astropa:astropa /usr/share/nginx/html && \
    chown -R astropa:astropa /var/cache/nginx && \
    chown -R astropa:astropa /var/log/nginx && \
    chown -R astropa:astropa /etc/nginx/conf.d

# Switch to non-root user
USER astropa

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD curl -f http://localhost:80/ || exit 1

# Expose port
EXPOSE 80

# Start nginx
CMD ["nginx", "-g", "daemon off;"]