INSERT INTO usuarios
(nombre, apellido, correo, contrasena, fecha_registro, nivel_cuenta, id_rol, id_estado_usuario)
SELECT 'Elizabeth', 'Reyes', 'bethdemo@gmail.com', '123456', CURDATE(), 5, 2, 1
    WHERE NOT EXISTS (
    SELECT 1 FROM usuarios WHERE correo = 'bethdemo@gmail.com'
);

INSERT INTO usuarios
(nombre, apellido, correo, contrasena, fecha_registro, nivel_cuenta, id_rol, id_estado_usuario)
SELECT 'Scarlett', 'Riquelme', 'scarlettdemo@gmail.com', '123456', CURDATE(), 4, 2, 1
    WHERE NOT EXISTS (
    SELECT 1 FROM usuarios WHERE correo = 'scarlettdemo@gmail.com'
);

INSERT INTO usuarios
(nombre, apellido, correo, contrasena, fecha_registro, nivel_cuenta, id_rol, id_estado_usuario)
SELECT 'Carlos', 'Guerrero', 'carlos@gmail.com', '123456', CURDATE(), 2, 1, 1
    WHERE NOT EXISTS (
    SELECT 1 FROM usuarios WHERE correo = 'carlos@gmail.com'
);

INSERT INTO usuarios
(nombre, apellido, correo, contrasena, fecha_registro, nivel_cuenta, id_rol, id_estado_usuario)
SELECT 'Fernanda', 'Luna', 'fernanda@gmail.com', '123456', CURDATE(), 7, 3, 1
    WHERE NOT EXISTS (
    SELECT 1 FROM usuarios WHERE correo = 'fernanda@gmail.com'
);

INSERT INTO usuarios
(nombre, apellido, correo, contrasena, fecha_registro, nivel_cuenta, id_rol, id_estado_usuario)
SELECT 'Bastian', 'Reyes', 'bastian@gmail.com', '123456', CURDATE(), 1, 1, 1
    WHERE NOT EXISTS (
    SELECT 1 FROM usuarios WHERE correo = 'bastian@gmail.com'
);

INSERT INTO usuarios
(nombre, apellido, correo, contrasena, fecha_registro, nivel_cuenta, id_rol, id_estado_usuario)
SELECT 'Camila', 'Torres', 'camila@gmail.com', '123456', CURDATE(), 2, 1, 1
    WHERE NOT EXISTS (
    SELECT 1 FROM usuarios WHERE correo = 'camila@gmail.com'
);

INSERT INTO usuarios
(nombre, apellido, correo, contrasena, fecha_registro, nivel_cuenta, id_rol, id_estado_usuario)
SELECT 'Lucas', 'Paredes', 'lucas@gmail.com', '123456', CURDATE(), 3, 3, 1
    WHERE NOT EXISTS (
    SELECT 1 FROM usuarios WHERE correo = 'lucas@gmail.com'
);

INSERT INTO usuarios
(nombre, apellido, correo, contrasena, fecha_registro, nivel_cuenta, id_rol, id_estado_usuario)
SELECT 'Antonia', 'Vega', 'antonia@gmail.com', '123456', CURDATE(), 2, 1, 2
    WHERE NOT EXISTS (
    SELECT 1 FROM usuarios WHERE correo = 'antonia@gmail.com'
);

INSERT INTO usuarios
(nombre, apellido, correo, contrasena, fecha_registro, nivel_cuenta, id_rol, id_estado_usuario)
SELECT 'Diego', 'Rojas', 'diego@gmail.com', '123456', CURDATE(), 1, 1, 3
    WHERE NOT EXISTS (
    SELECT 1 FROM usuarios WHERE correo = 'diego@gmail.com'
);

INSERT INTO usuarios
(nombre, apellido, correo, contrasena, fecha_registro, nivel_cuenta, id_rol, id_estado_usuario)
SELECT 'Valentina', 'Mora', 'valentina@gmail.com', '123456', CURDATE(), 4, 3, 1
    WHERE NOT EXISTS (
    SELECT 1 FROM usuarios WHERE correo = 'valentina@gmail.com'
);