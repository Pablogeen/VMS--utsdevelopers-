# Stage 1 — Build the application
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests -B

# Stage 2 — Run the application
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
COPY --from=builder /app/target/*.jar vms.jar
# Change ownership to non-root user
RUN chown appuser:appgroup vms.jar
USER appuser
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "vms.jar"]