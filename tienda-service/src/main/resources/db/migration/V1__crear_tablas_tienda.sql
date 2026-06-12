CREATE TABLE tipos_item (
    id_tipo_item BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_tipo VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE categorias_producto (
    id_categoria_producto BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_categoria VARCHAR(50) NOT NULL UNIQUE,
    descripcion VARCHAR(200)
);

CREATE TABLE estados_producto (
    id_estado_producto BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_estado VARCHAR(50) NOT NULL UNIQUE,
    descripcion VARCHAR(200)
);

CREATE TABLE productos_tienda (
    id_producto BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_producto VARCHAR(100) NOT NULL UNIQUE,
    descripcion VARCHAR(255) NOT NULL,
    precio INT NOT NULL,
    stock INT NOT NULL,
    id_referencia_item BIGINT NOT NULL,
    id_tipo_item BIGINT NOT NULL,
    id_categoria_producto BIGINT NOT NULL,
    id_estado_producto BIGINT NOT NULL,

    CONSTRAINT fk_producto_categoria
        FOREIGN KEY (id_categoria_producto)
            REFERENCES categorias_producto(id_categoria_producto),

    CONSTRAINT fk_producto_estado
        FOREIGN KEY (id_estado_producto)
            REFERENCES estados_producto(id_estado_producto),

    CONSTRAINT fk_producto_tipo
        FOREIGN KEY (id_tipo_item)
            REFERENCES tipos_item(id_tipo_item)
);

-- TIPOS DE ITEM
INSERT INTO tipos_item(nombre_tipo) VALUES
    ('ARMA'),
    ('POCION'),
    ('ARMADURA');

-- CATEGORIAS
INSERT INTO categorias_producto(nombre_categoria, descripcion) VALUES
    ('ARMAS', 'Productos de tipo arma'),
    ('OFERTAS', 'Productos con descuento');

-- ESTADOS
INSERT INTO estados_producto(nombre_estado, descripcion) VALUES
    ('DISPONIBLE', 'Producto disponible para compra'),
    ('AGOTADO', 'Producto sin stock'),
    ('INACTIVO', 'Producto oculto de la tienda');

-- PRODUCTOS
INSERT INTO productos_tienda(
    nombre_producto,
    descripcion,
    precio,
    stock,
    id_referencia_item,
    id_tipo_item,
    id_categoria_producto,
    id_estado_producto
) VALUES
      ('Espada del Novato en Tienda', 'Espada basica para jugadores nuevos', 500, 20, 1, 1, 1, 1),
      ('Arco del Bosque en Tienda', 'Arco recomendado para personajes de nivel bajo', 1200, 10, 2, 1, 1, 1),
      ('Baston Arcano en Tienda', 'Baston magico para magos', 3000, 8, 3, 1, 1, 1),
      ('Espada Legendaria en Tienda', 'Arma poderosa de alto nivel', 10000, 2, 4, 1, 1, 1),
      ('Arco Sombrio en Oferta', 'Arco epico con descuento temporal', 6500, 5, 5, 1, 2, 1),
      ('Baston Supremo Imperial', 'Baston legendario de magia avanzada', 15000, 1, 6, 1, 1, 1);