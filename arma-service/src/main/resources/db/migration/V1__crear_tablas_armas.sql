CREATE TABLE tipos_arma (

                            id_tipo_arma BIGINT AUTO_INCREMENT PRIMARY KEY,

                            nombre_tipo VARCHAR(50) NOT NULL UNIQUE,

                            descripcion VARCHAR(200)
);

CREATE TABLE rarezas_arma (

                              id_rareza_arma BIGINT AUTO_INCREMENT PRIMARY KEY,

                              nombre_rareza VARCHAR(50) NOT NULL UNIQUE,

                              multiplicador_danio DOUBLE NOT NULL,

                              descripcion VARCHAR(200)
);

CREATE TABLE armas (

                       id_arma BIGINT AUTO_INCREMENT PRIMARY KEY,

                       nombre_arma VARCHAR(100) NOT NULL UNIQUE,

                       danio INT NOT NULL,

                       nivel_minimo INT NOT NULL,

                       precio INT NOT NULL,

                       id_tipo_arma BIGINT NOT NULL,

                       id_rareza_arma BIGINT NOT NULL,

                       CONSTRAINT fk_arma_tipo
                           FOREIGN KEY (id_tipo_arma)
                               REFERENCES tipos_arma(id_tipo_arma),

                       CONSTRAINT fk_arma_rareza
                           FOREIGN KEY (id_rareza_arma)
                               REFERENCES rarezas_arma(id_rareza_arma)
);

-- TIPOS DE ARMAS

INSERT INTO tipos_arma(nombre_tipo, descripcion) VALUES
                                                     ('ESPADA', 'Arma cuerpo a cuerpo'),
                                                     ('ARCO', 'Arma de largo alcance'),
                                                     ('BASTON', 'Arma mágica');

-- RAREZAS

INSERT INTO rarezas_arma(nombre_rareza, multiplicador_danio, descripcion) VALUES
                                                                              ('COMUN', 1.0, 'Rareza básica'),
                                                                              ('RARO', 1.5, 'Rareza intermedia'),
                                                                              ('EPICO', 2.0, 'Rareza avanzada'),
                                                                              ('LEGENDARIO', 3.0, 'Rareza máxima');

-- ARMAS INICIALES

INSERT INTO armas(
    nombre_arma,
    danio,
    nivel_minimo,
    precio,
    id_tipo_arma,
    id_rareza_arma
) VALUES

      ('Espada del Novato', 15, 1, 500, 1, 1),

      ('Arco del Bosque', 25, 3, 1200, 2, 2),

      ('Baston Arcano', 40, 5, 3000, 3, 3),

      ('Espada Legendaria', 80, 10, 10000, 1, 4),

      ('Arco Celestial', 70, 9, 8500, 2, 4),

      ('Baston Supremo', 90, 12, 15000, 3, 4);