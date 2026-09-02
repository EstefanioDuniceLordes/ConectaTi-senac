# ConectaTI Senac

Sistema de chamados de manutencao com backend Spring Boot e frontend React/Vite.

## Requisitos

- Java 17+
- Node.js 20+
- Docker

## Rodar o backend

```powershell
cd backend/conectati-api/conectati-api
docker compose up -d
.\gradlew.bat bootRun
```

A API fica em `http://localhost:8080`.

Swagger: `http://localhost:8080/swagger-ui/index.html`

## Rodar o frontend

```powershell
cd "frontend/web-mobile/conectati-senac-react (1)"
npm.cmd install
npm.cmd run dev
```

O frontend fica em `http://127.0.0.1:5173`.

## Usuarios demo

- Administrador: `admin@conectati.local` / `Admin@123`
- Instrutor: `instrutor@conectati.local` / `Instrutor@123`
- Tecnico: `tecnico@conectati.local` / `Tecnico@123`
- Coordenador: `coordenador@conectati.local` / `Coordenador@123`

## Validacao

```powershell
cd backend/conectati-api/conectati-api
.\gradlew.bat build

cd "../../../frontend/web-mobile/conectati-senac-react (1)"
npm.cmd run build
```
