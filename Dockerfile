# ---------- ETAPA 1: build ----------
FROM gradle:8.6-jdk21-alpine AS build
WORKDIR /app

# Copia os arquivos de configuracao do Gradle
COPY build.gradle settings.gradle* ./
COPY src ./src

# Executa o build sem rodar os testes unitarios
RUN gradle clean bootJar -x test --no-daemon

# ---------- ETAPA 2: run ----------
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Desativa a geracao do arquivo jar-plain para evitar conflitos no COPY
ENV GRADLE_OPTS="-Dorg.gradle.daemon=false"

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]