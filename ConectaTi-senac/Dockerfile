# ---------- ETAPA 1: build ----------
FROM gradle:8.6-jdk21-alpine AS build
WORKDIR /app

# Copia os arquivos de configuracao do Gradle e codigo fonte do backend
COPY backend/conectati-api/conectati-api/build.gradle backend/conectati-api/conectati-api/settings.gradle* ./
COPY backend/conectati-api/conectati-api/src ./src

RUN gradle clean bootJar -x test --no-daemon

# ---------- ETAPA 2: run ----------
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

ENV PORT=8080
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

