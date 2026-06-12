CREATE TABLE tipos_item (
    id_tipo_item BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_tipo VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE estados_inventario (
    id_estado_inventario BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_estado VARCHAR(50) NOT NULL UNIQUE,
    descripcion VARCHAR(200)
);

CREATE TABLE inventarios (
    id_inventario BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_personaje BIGINT NOT NULL,
    capacidad_maxima INT NOT NULL,
    espacios_usados INT NOT NULL,
    fecha_creacion DATE NOT NULL,
    id_estado_inventario BIGINT NOT NULL,

    CONSTRAINT fk_inventario_estado
        FOREIGN KEY (id_estado_inventario)
            REFERENCES estados_inventario(id_estado_inventario)
);

CREATE TABLE items_inventario (
    id_item_inventario BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_inventario BIGINT NOT NULL,
    id_referencia_item BIGINT NOT NULL,
    id_tipo_item BIGINT NOT NULL,
    cantidad INT NOT NULL,
    fecha_obtencion DATE NOT NULL,
    equipado BOOLEAN NOT NULL,

    CONSTRAINT fk_item_inventario
        FOREIGN KEY (id_inventario)
            REFERENCES inventarios(id_inventario),

    CONSTRAINT fk_item_tipo
        FOREIGN KEY (id_tipo_item)
            REFERENCES tipos_item(id_tipo_item)
);

INSERT INTO tipos_item(nombre_tipo) VALUES
    ('ARMA'),
    ('POCION'),
    ('ARMADURA');

INSERT INTO estados_inventario(nombre_estado, descripcion) VALUES
    ('ACTIVO', 'Inventario disponible'),
    ('LLENO', 'Inventario sin espacios disponibles'),
    ('BLOQUEADO', 'Inventario bloqueado temporalmente');

INSERT INTO inventarios(id_personaje, capacidad_maxima, espacios_usados, fecha_creacion, id_estado_inventario) VALUES
    (1, 30, 2, CURDATE(), 1),
    (2, 30, 1, CURDATE(), 1),
    (3, 40, 1, CURDATE(), 1),
    (4, 50, 2, CURDATE(), 1),
    (5, 25, 1, CURDATE(), 1),
    (6, 35, 1, CURDATE(), 1),
    (7, 30, 1, CURDATE(), 1),
    (8, 20, 1, CURDATE(), 1),
    (9, 30, 1, CURDATE(), 1),
    (10, 45, 2, CURDATE(), 1);

INSERT INTO items_inventario(id_inventario, id_referencia_item, id_tipo_item, cantidad, fecha_obtencion, equipado) VALUES
    (1, 1, 1, 1, CURDATE(), true),
    (1, 2, 1, 1, CURDATE(), false),
    (2, 3, 1, 1, CURDATE(), true),
    (3, 4, 1, 1, CURDATE(), false),
    (4, 5, 1, 1, CURDATE(), true),
    (5, 1, 1, 1, CURDATE(), false),
    (6, 2, 1, 1, CURDATE(), true),
    (7, 3, 1, 1, CURDATE(), false),
    (8, 4, 1, 1, CURDATE(), true),
    (9, 5, 1, 1, CURDATE(), false),
    (10, 1, 1, 1, CURDATE(), true),
    (10, 2, 1, 1, CURDATE(), false);