CREATE TABLE roles (
                       id_rol BIGINT AUTO_INCREMENT PRIMARY KEY,
                       nombre_rol VARCHAR(50) NOT NULL UNIQUE,
                       descripcion VARCHAR(200)
);

CREATE TABLE estados_usuario (
                                 id_estado_usuario BIGINT AUTO_INCREMENT PRIMARY KEY,
                                 nombre_estado_usuario VARCHAR(50) NOT NULL UNIQUE,
                                 descripcion VARCHAR(200)
);

CREATE TABLE usuarios (
                          id_usuario BIGINT AUTO_INCREMENT PRIMARY KEY,
                          nombre VARCHAR(50) NOT NULL,
                          apellido VARCHAR(50) NOT NULL,
                          correo VARCHAR(255) NOT NULL UNIQUE,
                          contrasena VARCHAR(100) NOT NULL,
                          fecha_registro DATE NOT NULL,
                          nivel_cuenta INT NOT NULL,
                          id_rol BIGINT NOT NULL,
                          id_estado_usuario BIGINT NOT NULL,

                          CONSTRAINT fk_usuario_rol
                              FOREIGN KEY (id_rol) REFERENCES roles(id_rol),

                          CONSTRAINT fk_usuario_estado
                              FOREIGN KEY (id_estado_usuario) REFERENCES estados_usuario(id_estado_usuario)
);

INSERT INTO roles (nombre_rol, descripcion) VALUES
('JUGADOR', 'Usuario normal del videojuego'),
('ADMIN', 'Administrador del sistema'),
('VIP', 'Usuario con beneficios especiales');

INSERT INTO estados_usuario (nombre_estado_usuario, descripcion) VALUES
('ACTIVO', 'Cuenta habilitada para jugar'),
('SUSPENDIDO', 'Cuenta suspendida temporalmente'),
('BANEADO', 'Cuenta bloqueada por incumplir reglas');