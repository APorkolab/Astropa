# Backend Dockerfile - Multi-stage build for optimization
FROM maven:3.9.4-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy pom.xml first to leverage Docker cache layers
COPY pom.xml .

# Download dependencies (cached layer if pom.xml doesn't change)
RUN mvn dependency:resolve

# Copy source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Production stage
FROM eclipse-temurin:21-jre-alpine

# Create non-root user for security
RUN addgroup -g 1001 -S astropa && \
    adduser -S astropa -u 1001 -G astropa

WORKDIR /app

# Install curl for health checks
RUN apk add --no-cache curl

# Copy the built jar from builder stage
COPY --from=builder /app/target/*.jar app.jar

# Change ownership to non-root user
RUN chown -R astropa:astropa /app

USER astropa

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# Expose port
EXPOSE 8080

# JVM optimization flags
ENV JAVA_OPTS="-Xms512m -Xmx1g -XX:+UseG1GC -XX:G1HeapRegionSize=16m -XX:+UseStringDeduplication"

# Run the application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]