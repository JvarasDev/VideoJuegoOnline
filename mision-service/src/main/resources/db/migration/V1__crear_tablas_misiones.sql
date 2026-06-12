CREATE TABLE tipos_mision (

                              id_tipo_mision BIGINT AUTO_INCREMENT PRIMARY KEY,

                              nombre_tipo VARCHAR(50) NOT NULL UNIQUE,

                              descripcion VARCHAR(200)
);

CREATE TABLE estados_mision (

                                id_estado_mision BIGINT AUTO_INCREMENT PRIMARY KEY,

                                nombre_estado VARCHAR(50) NOT NULL UNIQUE,

                                descripcion VARCHAR(200)
);

CREATE TABLE misiones (

                          id_mision BIGINT AUTO_INCREMENT PRIMARY KEY,

                          nombre_mision VARCHAR(100) NOT NULL UNIQUE,

                          descripcion VARCHAR(255) NOT NULL,

                          recompensa_experiencia INT NOT NULL,

                          recompensa_monedas INT NOT NULL,

                          nivel_minimo INT NOT NULL,

                          id_tipo_mision BIGINT NOT NULL,

                          id_estado_mision BIGINT NOT NULL,

                          CONSTRAINT fk_mision_tipo
                              FOREIGN KEY (id_tipo_mision)
                                  REFERENCES tipos_mision(id_tipo_mision),

                          CONSTRAINT fk_mision_estado
                              FOREIGN KEY (id_estado_mision)
                                  REFERENCES estados_mision(id_estado_mision)
);

-- TIPOS DE MISIONES

INSERT INTO tipos_mision(nombre_tipo, descripcion) VALUES
                                                       ('DIARIA', 'Mision que se puede completar cada dia'),
                                                       ('HISTORIA', 'Mision principal del juego'),
                                                       ('EVENTO', 'Mision temporal por evento especial');

-- ESTADOS DE MISION

INSERT INTO estados_mision(nombre_estado, descripcion) VALUES
                                                           ('ACTIVA', 'Mision disponible'),
                                                           ('INACTIVA', 'Mision no disponible'),
                                                           ('FINALIZADA', 'Mision completada');

-- MISIONES INICIALES

INSERT INTO misiones(
    nombre_mision,
    descripcion,
    recompensa_experiencia,
    recompensa_monedas,
    nivel_minimo,
    id_tipo_mision,
    id_estado_mision
) VALUES

      ('Derrotar lobos del bosque',
       'Eliminar 5 lobos cercanos al pueblo',
       100,
       50,
       1,
       1,
       1),

      ('Rescatar al aldeano',
       'Completar una mision principal de rescate',
       500,
       200,
       3,
       2,
       1),

      ('Festival del Dragon',
       'Participar en evento especial contra dragones',
       1000,
       500,
       8,
       3,
       1),

      ('Entrenamiento basico',
       'Aprender controles y habilidades iniciales',
       50,
       20,
       1,
       1,
       1),

      ('Defensa del castillo',
       'Proteger el castillo durante el ataque enemigo',
       800,
       350,
       6,
       2,
       1),

      ('Exploracion del norte',
       'Descubrir nuevas zonas heladas',
       300,
       120,
       4,
       1,
       1),

      ('Caceria de monstruos',
       'Eliminar criaturas peligrosas del bosque oscuro',
       600,
       280,
       5,
       2,
       1),

      ('Evento lluvia de fuego',
       'Participar en el evento especial volcanico',
       1500,
       700,
       10,
       3,
       1);