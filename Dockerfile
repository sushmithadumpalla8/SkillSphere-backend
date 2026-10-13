# Stage 1: Build the Spring Boot application using Maven
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app

# Cache dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B || true

# Copy application source and build jar package
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Lean production runtime container
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Create unprivileged system user for secure container execution
RUN groupadd -r spring && useradd -r -g spring spring
USER spring:spring

# Copy jar from builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose default port (Render dynamically binds $PORT)
ENV PORT=8080
EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java -XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -Dserver.port=${PORT:-8080} -Djava.security.egd=file:/dev/./urandom -jar app.jar"]
