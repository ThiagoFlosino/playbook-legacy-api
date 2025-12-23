# Estágio 1: Build (Compilação)
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
# Build pulando testes unitários para ser mais rápido agora
RUN mvn clean package -DskipTests

# Estágio 2: Runtime (Execução)
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# Cria um usuário não-root por segurança (Best Practice)
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copia apenas o JAR gerado no estágio anterior
COPY --from=builder /app/target/*.jar app.jar

# Expõe a porta e define o comando de entrada
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]