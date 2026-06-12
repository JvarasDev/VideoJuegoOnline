CREATE TABLE metodos_pago (

                              id_metodo_pago BIGINT AUTO_INCREMENT PRIMARY KEY,

                              nombre_metodo VARCHAR(50) NOT NULL UNIQUE,

                              descripcion VARCHAR(200)
);

CREATE TABLE estados_pago (

                              id_estado_pago BIGINT AUTO_INCREMENT PRIMARY KEY,

                              nombre_estado VARCHAR(50) NOT NULL UNIQUE,

                              descripcion VARCHAR(200)
);

CREATE TABLE pagos (

                       id_pago BIGINT AUTO_INCREMENT PRIMARY KEY,

                       id_usuario BIGINT NOT NULL,

                       id_producto BIGINT NOT NULL,

                       monto INT NOT NULL,

                       fecha_pago DATETIME NOT NULL,

                       codigo_transaccion VARCHAR(100) NOT NULL UNIQUE,

                       id_metodo_pago BIGINT NOT NULL,

                       id_estado_pago BIGINT NOT NULL,

                       CONSTRAINT fk_pago_metodo
                           FOREIGN KEY (id_metodo_pago)
                               REFERENCES metodos_pago(id_metodo_pago),

                       CONSTRAINT fk_pago_estado
                           FOREIGN KEY (id_estado_pago)
                               REFERENCES estados_pago(id_estado_pago)
);

-- METODOS DE PAGO

INSERT INTO metodos_pago(nombre_metodo, descripcion) VALUES
                                                         ('TARJETA', 'Pago con tarjeta bancaria'),
                                                         ('TRANSFERENCIA', 'Pago por transferencia'),
                                                         ('MONEDAS_JUEGO', 'Pago con monedas internas del videojuego');

-- ESTADOS DE PAGO

INSERT INTO estados_pago(nombre_estado, descripcion) VALUES
                                                         ('APROBADO', 'Pago realizado correctamente'),
                                                         ('RECHAZADO', 'Pago rechazado'),
                                                         ('PENDIENTE', 'Pago en espera de confirmacion');

-- PAGOS INICIALES

INSERT INTO pagos(
    id_usuario,
    id_producto,
    monto,
    fecha_pago,
    codigo_transaccion,
    id_metodo_pago,
    id_estado_pago
) VALUES

      (1, 1, 500, NOW(), 'TX-0001', 1, 1),

      (3, 2, 1200, NOW(), 'TX-0002', 2, 1),

      (4, 3, 3000, NOW(), 'TX-0003', 3, 1),

      (5, 4, 10000, NOW(), 'TX-0004', 1, 1),

      (6, 5, 8500, NOW(), 'TX-0005', 2, 2),

      (7, 6, 15000, NOW(), 'TX-0006', 1, 3),

      (10, 1, 500, NOW(), 'TX-0007', 3, 1);