-- Script de creación de base de datos y tabla para H2/MySQL

-- Crear base de datos (para MySQL - ejecutar manualmente si usas MySQL)
-- CREATE DATABASE IF NOT EXISTS tareas_db
-- CHARACTER SET utf8mb4
-- COLLATE utf8mb4_unicode_ci;

-- USE tareas_db;

-- Crear tabla de tareas
DROP TABLE IF EXISTS tareas;

CREATE TABLE tareas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    descripcion VARCHAR(500) NOT NULL,
    estado VARCHAR(20) NOT NULL DEFAULT 'PENDIENTE' CHECK (estado IN ('PENDIENTE', 'EN_PROCESO', 'COMPLETADA')),
    fecha_creacion DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_titulo_minimo CHECK (CHAR_LENGTH(titulo) >= 1),
    CONSTRAINT chk_descripcion_minimo CHECK (CHAR_LENGTH(descripcion) >= 5)
);

-- Crear índices
CREATE INDEX idx_estado ON tareas(estado);
CREATE INDEX idx_fecha_creacion ON tareas(fecha_creacion);

-- Insertar datos de prueba
INSERT INTO tareas (titulo, descripcion, estado, fecha_creacion)
VALUES
('Estudiar Spring Boot', 'Completar el taller práctico de AD2', 'PENDIENTE', CURDATE()),
('Revisar código', 'Hacer code review del proyecto de gestión de tareas', 'EN_PROCESO', CURDATE()),
('Preparar presentación', 'Crear slides para la demo del proyecto', 'COMPLETADA', CURDATE()),
('Configurar base de datos', 'Instalar y configurar MySQL para el proyecto', 'PENDIENTE', CURDATE()),
('Escribir documentación', 'Documentar la API REST con ejemplos', 'PENDIENTE', CURDATE());