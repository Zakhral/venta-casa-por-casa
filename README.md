# Proyecto: Venta Casa por Casa

Aplicación móvil desarrollada como parte del examen práctico de la materia, utilizando **Android Studio con Kotlin** como frontend y **Node.js con Express, Sequelize y SQLite** como backend.

El sistema permite gestionar un negocio de venta casa por casa mediante los siguientes módulos:

- Autenticación
- Registro de tienda
- Gestión de productos
- Gestión de clientes
- Registro de ventas
- Reportes por periodo

---

## Estructura del proyecto

El repositorio está organizado en dos carpetas principales:

- `frontend/` → aplicación móvil Android desarrollada en Kotlin
- `backend/` → servidor API REST desarrollado con Node.js, Express, Sequelize y SQLite

---

## Tecnologías utilizadas

### Frontend
- Android Studio
- Kotlin
- Retrofit
- ViewBinding

### Backend
- Node.js
- Express
- Sequelize
- SQLite
- CORS

---

## Requisitos previos

Antes de ejecutar el proyecto se debe tener instalado lo siguiente:

- [Node.js](https://nodejs.org/)
- Android Studio
- Emulador Android o dispositivo físico
- Git
- VS Code o editor similar para el backend

---

## Credenciales de acceso

El sistema utiliza un usuario predeterminado para el inicio de sesión:

- **Usuario:** `admin`
- **Contraseña:** `admin123`

---

## Instrucciones para ejecutar el backend

### Abrir el proyecto desde vscode en la carpeta del backend y en una nueva terminal

- inicia el servidor - node app.js
- Si todo está correcto, la terminal debe mostrar mensajes similares a los siguientes:

Conexión a SQLite establecida correctamente.
Base de datos sincronizada correctamente.
El usuario admin ya existe.
Servidor corriendo en http://localhost:3000

- Mantener el backend encendido

## Instrucciones para ejecutar el frontend

### Abrir el proyecto Desde Android Studio en la carpeta del frontend

- Esperar la sincronización de Gradle
- Ejecutar la aplicación en el emulador o dispositivo