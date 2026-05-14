# API de Gestión de Tareas

## Descripción del Proyecto

Esta es una API REST desarrollada en Spring Boot para la gestión de tareas. Permite crear, leer, actualizar y eliminar tareas, con validaciones de estado y lógica de negocio específica. El proyecto incluye una base de datos H2 embebida para desarrollo y pruebas.

### Características Principales

- **Gestión CRUD completa**: Crear, leer, actualizar y eliminar tareas
- **Validación de estados**: Control de transiciones entre estados de tarea (PENDIENTE → EN_PROCESO → COMPLETADA)
- **Validación de entrada**: Uso de Bean Validation para asegurar datos correctos
- **Manejo de excepciones**: Respuestas estructuradas para errores
- **Base de datos H2**: Configuración automática con datos de prueba
- **CORS habilitado**: Para desarrollo frontend

### Tecnologías Utilizadas

- **Spring Boot 3.2.5**: Framework principal
- **Java 17**: Lenguaje de programación
- **Spring Data JPA**: Para persistencia de datos
- **H2 Database**: Base de datos embebida
- **Maven**: Gestor de dependencias
- **Bean Validation**: Validación de entrada

## Estructura del Proyecto

```
src/
├── main/
│   ├── java/
│   │   └── ApiGestionTareascom/
│   │       └── example/
│   │           ├── controller/
│   │           │   └── TareaController.java
│   │           ├── demo/
│   │           │   └── DemoApplication.java
│   │           ├── dto/
│   │           │   └── TareaDTO.java
│   │           ├── entity/
│   │           │   └── Tarea.java
│   │           ├── exception/
│   │           │   └── GlobalExceptionHandler.java
│   │           ├── repository/
│   │           │   └── TareaRepository.java
│   │           └── service/
│   │               └── TareaService.java
│   └── resources/
│       ├── application.properties
│       └── templates/
│           └── schema.sql
└── test/
    └── java/
        └── ApiGestionTareascom/
            └── example/
                └── demo/
                    └── DemoApplicationTests.java
```

## Configuración y Ejecución

### Prerrequisitos

- Java 17 o superior
- Maven 3.6+

### Ejecución

1. Clona o descarga el proyecto
2. Navega al directorio raíz del proyecto
3. Ejecuta el comando:

```bash
mvn spring-boot:run
```

La aplicación se iniciará en el puerto 8080.

### Base de Datos

La aplicación utiliza H2 Database embebida. Al iniciar, se crea automáticamente la tabla `tareas` y se insertan 5 tareas de ejemplo.

**Acceso a la consola H2:**
- URL: http://localhost:8080/h2-console
- JDBC URL: jdbc:h2:mem:testdb
- Usuario: sa
- Contraseña: (vacía)

## API Endpoints

### Base URL
```
http://localhost:8080/api/tareas
```

### 1. Obtener Todas las Tareas
**GET** `/api/tareas`

**Respuesta exitosa (200):**
```json
[
  {
    "id": 1,
    "titulo": "Revisar documentación",
    "descripcion": "Revisar la documentación del proyecto",
    "estado": "PENDIENTE",
    "fechaCreacion": "2024-01-15T10:00:00"
  },
  {
    "id": 2,
    "titulo": "Implementar funcionalidad",
    "descripcion": "Implementar la nueva funcionalidad",
    "estado": "EN_PROCESO",
    "fechaCreacion": "2024-01-15T11:00:00"
  }
]
```

**Ejemplo con curl:**
```bash
curl -X GET http://localhost:8080/api/tareas
```

### 2. Obtener Tarea por ID
**GET** `/api/tareas/{id}`

**Parámetros:**
- `id` (path): ID de la tarea (Long)

**Respuesta exitosa (200):**
```json
{
  "id": 1,
  "titulo": "Revisar documentación",
  "descripcion": "Revisar la documentación del proyecto",
  "estado": "PENDIENTE",
  "fechaCreacion": "2024-01-15T10:00:00"
}
```

**Respuesta de error (404):**
```json
{
  "timestamp": "2024-01-15T12:00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Tarea no encontrada con ID: 1"
}
```

**Ejemplo con curl:**
```bash
curl -X GET http://localhost:8080/api/tareas/1
```

### 3. Obtener Tareas por Estado
**GET** `/api/tareas/estado/{estado}`

**Parámetros:**
- `estado` (path): Estado de la tarea (PENDIENTE, EN_PROCESO, COMPLETADA)

**Respuesta exitosa (200):**
```json
[
  {
    "id": 1,
    "titulo": "Revisar documentación",
    "descripcion": "Revisar la documentación del proyecto",
    "estado": "PENDIENTE",
    "fechaCreacion": "2024-01-15T10:00:00"
  }
]
```

**Ejemplo con curl:**
```bash
curl -X GET http://localhost:8080/api/tareas/estado/PENDIENTE
```

### 4. Crear Nueva Tarea
**POST** `/api/tareas`

**Cuerpo de la solicitud:**
```json
{
  "titulo": "Nueva tarea",
  "descripcion": "Descripción de la nueva tarea",
  "estado": "PENDIENTE"
}
```

**Respuesta exitosa (201):**
```json
{
  "id": 6,
  "titulo": "Nueva tarea",
  "descripcion": "Descripción de la nueva tarea",
  "estado": "PENDIENTE",
  "fechaCreacion": "2024-01-15T12:00:00"
}
```

**Respuesta de error (400) - Validación:**
```json
{
  "timestamp": "2024-01-15T12:00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "titulo: no debe estar vacío"
}
```

**Ejemplo con curl:**
```bash
curl -X POST http://localhost:8080/api/tareas \
  -H "Content-Type: application/json" \
  -d '{
    "titulo": "Nueva tarea",
    "descripcion": "Descripción de la nueva tarea",
    "estado": "PENDIENTE"
  }'
```

### 5. Actualizar Tarea
**PUT** `/api/tareas/{id}`

**Parámetros:**
- `id` (path): ID de la tarea a actualizar

**Cuerpo de la solicitud:**
```json
{
  "titulo": "Tarea actualizada",
  "descripcion": "Descripción actualizada",
  "estado": "EN_PROCESO"
}
```

**Respuesta exitosa (200):**
```json
{
  "id": 1,
  "titulo": "Tarea actualizada",
  "descripcion": "Descripción actualizada",
  "estado": "EN_PROCESO",
  "fechaCreacion": "2024-01-15T10:00:00"
}
```

**Respuesta de error (400) - Transición inválida:**
```json
{
  "timestamp": "2024-01-15T12:00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "No se puede cambiar el estado de PENDIENTE directamente a COMPLETADA"
}
```

**Ejemplo con curl:**
```bash
curl -X PUT http://localhost:8080/api/tareas/1 \
  -H "Content-Type: application/json" \
  -d '{
    "titulo": "Tarea actualizada",
    "descripcion": "Descripción actualizada",
    "estado": "EN_PROCESO"
  }'
```

### 6. Eliminar Tarea
**DELETE** `/api/tareas/{id}`

**Parámetros:**
- `id` (path): ID de la tarea a eliminar

**Respuesta exitosa (204):** Sin contenido

**Respuesta de error (404):**
```json
{
  "timestamp": "2024-01-15T12:00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Tarea no encontrada con ID: 1"
}
```

**Ejemplo con curl:**
```bash
curl -X DELETE http://localhost:8080/api/tareas/1
```

## Reglas de Negocio

### Transiciones de Estado
- **PENDIENTE** → **EN_PROCESO** ✅
- **EN_PROCESO** → **COMPLETADA** ✅
- **PENDIENTE** → **COMPLETADA** ❌ (No permitido)

### Validaciones
- `titulo`: No puede estar vacío
- `descripcion`: No puede estar vacío
- `estado`: Debe ser uno de los valores válidos (PENDIENTE, EN_PROCESO, COMPLETADA)

## Manejo de Errores

La API utiliza un manejador global de excepciones que devuelve respuestas JSON estructuradas:

```json
{
  "timestamp": "2024-01-15T12:00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Descripción del error"
}
```

## Pruebas

Para ejecutar las pruebas:

```bash
mvn test
```

## Contribución

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/nueva-funcionalidad`)
3. Commit tus cambios (`git commit -am 'Agrega nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Abre un Pull Request

## Licencia

Este proyecto está bajo la Licencia MIT.