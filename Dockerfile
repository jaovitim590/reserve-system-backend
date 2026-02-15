# Stage 1: Build
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /workspace/app

# Copia arquivos do projeto
COPY pom.xml .
COPY src src

# Instala Maven e faz o build
RUN apk add --no-cache maven && \
    mvn clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copia o JAR compilado
COPY --from=build /workspace/app/target/ReservSystem.jar app.jar

# Cria diretório para dados do H2
RUN mkdir -p /app/data

# Expõe a porta
EXPOSE 8081

# Comando de inicialização
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]