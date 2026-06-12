CREATE TABLE tipos_combate (
                               id_tipo_combate BIGINT AUTO_INCREMENT PRIMARY KEY,
                               nombre_tipo VARCHAR(50) NOT NULL UNIQUE,
                               descripcion VARCHAR(200),
                               recompensa_base INT NOT NULL
);

CREATE TABLE estados_combate (
                                 id_estado_combate BIGINT AUTO_INCREMENT PRIMARY KEY,
                                 nombre_estado VARCHAR(50) NOT NULL UNIQUE,
                                 descripcion VARCHAR(200)
);

CREATE TABLE combates (
                          id_combate BIGINT AUTO_INCREMENT PRIMARY KEY,
                          id_personaje_atacante BIGINT NOT NULL,
                          id_personaje_defensor BIGINT NOT NULL,
                          id_ganador BIGINT NOT NULL,
                          fecha_combate DATETIME NOT NULL,
                          experiencia_ganada INT NOT NULL,
                          monedas_ganadas INT NOT NULL,
                          duracion_segundos INT NOT NULL,
                          id_tipo_combate BIGINT NOT NULL,
                          id_estado_combate BIGINT NOT NULL,

                          CONSTRAINT fk_combate_tipo
                              FOREIGN KEY (id_tipo_combate)
                                  REFERENCES tipos_combate(id_tipo_combate),

                          CONSTRAINT fk_combate_estado
                              FOREIGN KEY (id_estado_combate)
                                  REFERENCES estados_combate(id_estado_combate)
);

INSERT INTO tipos_combate(nombre_tipo, descripcion, recompensa_base) VALUES
                                                                         ('PVP', 'Combate jugador contra jugador', 100),
                                                                         ('PVE', 'Combate jugador contra enemigo', 80),
                                                                         ('ARENA', 'Combate competitivo en arena', 150);

INSERT INTO estados_combate(nombre_estado, descripcion) VALUES
                                                            ('FINALIZADO', 'Combate terminado'),
                                                            ('EN_PROCESO', 'Combate en curso'),
                                                            ('CANCELADO', 'Combate cancelado');

INSERT INTO combates(
    id_personaje_atacante,
    id_personaje_defensor,
    id_ganador,
    fecha_combate,
    experiencia_ganada,
    monedas_ganadas,
    duracion_segundos,
    id_tipo_combate,
    id_estado_combate
) VALUES
      (1, 2, 1, NOW(), 120, 50, 180, 1, 1),
      (2, 3, 3, NOW(), 150, 70, 240, 1, 1),
      (3, 4, 4, NOW(), 200, 100, 300, 2, 1),
      (4, 5, 4, NOW(), 250, 120, 360, 3, 1),
      (5, 6, 6, NOW(), 180, 90, 210, 2, 1),
      (6, 7, 7, NOW(), 220, 110, 260, 1, 1),
      (7, 8, 7, NOW(), 170, 80, 200, 2, 1),
      (8, 9, 9, NOW(), 300, 150, 400, 3, 1),
      (9, 10, 10, NOW(), 320, 170, 420, 3, 1),
      (10, 1, 1, NOW(), 260, 130, 310, 1, 1);