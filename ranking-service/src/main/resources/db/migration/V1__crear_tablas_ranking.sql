CREATE TABLE temporadas (

                            id_temporada BIGINT AUTO_INCREMENT PRIMARY KEY,

                            nombre_temporada VARCHAR(100) NOT NULL UNIQUE,

                            fecha_inicio DATE NOT NULL,

                            fecha_fin DATE NOT NULL
);

CREATE TABLE ligas (

                       id_liga BIGINT AUTO_INCREMENT PRIMARY KEY,

                       nombre_liga VARCHAR(50) NOT NULL UNIQUE,

                       puntos_minimos INT NOT NULL,

                       puntos_maximos INT NOT NULL,

                       descripcion VARCHAR(200)
);

CREATE TABLE rankings (

                          id_ranking BIGINT AUTO_INCREMENT PRIMARY KEY,

                          id_personaje BIGINT NOT NULL,

                          puntos INT NOT NULL,

                          victorias INT NOT NULL,

                          derrotas INT NOT NULL,

                          posicion INT NOT NULL,

                          id_temporada BIGINT NOT NULL,

                          id_liga BIGINT NOT NULL,

                          CONSTRAINT fk_ranking_temporada
                              FOREIGN KEY (id_temporada)
                                  REFERENCES temporadas(id_temporada),

                          CONSTRAINT fk_ranking_liga
                              FOREIGN KEY (id_liga)
                                  REFERENCES ligas(id_liga)
);

INSERT INTO temporadas(
    nombre_temporada,
    fecha_inicio,
    fecha_fin
) VALUES
      ('Temporada Inicial', CURDATE(), DATE_ADD(CURDATE(), INTERVAL 30 DAY)),
      ('Temporada Dragon', CURDATE(), DATE_ADD(CURDATE(), INTERVAL 60 DAY));

INSERT INTO ligas(
    nombre_liga,
    puntos_minimos,
    puntos_maximos,
    descripcion
) VALUES
      ('BRONCE', 0, 999, 'Liga inicial'),
      ('PLATA', 1000, 1999, 'Liga intermedia'),
      ('ORO', 2000, 3999, 'Liga avanzada'),
      ('DIAMANTE', 4000, 9999, 'Liga competitiva alta');

INSERT INTO rankings(
    id_personaje,
    puntos,
    victorias,
    derrotas,
    posicion,
    id_temporada,
    id_liga
) VALUES
      (1, 500, 5, 2, 10, 1, 1),
      (2, 900, 8, 4, 9, 1, 1),
      (3, 1200, 10, 4, 8, 1, 2),
      (4, 1800, 14, 5, 7, 1, 2),
      (5, 2400, 18, 7, 6, 1, 3),
      (6, 3000, 22, 8, 5, 1, 3),
      (7, 3500, 25, 9, 4, 1, 3),
      (8, 4200, 30, 10, 3, 1, 4),
      (9, 4800, 35, 11, 2, 1, 4),
      (10, 5500, 40, 12, 1, 1, 4);