CREATE TABLE clases_personaje (

                                  id_clase_personaje BIGINT PRIMARY KEY AUTO_INCREMENT,

                                  nombre_clase VARCHAR(100) NOT NULL,

                                  descripcion VARCHAR(255)
);

CREATE TABLE estados_personaje (

                                   id_estado_personaje BIGINT PRIMARY KEY AUTO_INCREMENT,

                                   nombre_estado VARCHAR(100) NOT NULL
);

CREATE TABLE personajes (

                            id_personaje BIGINT PRIMARY KEY AUTO_INCREMENT,

                            nombre VARCHAR(100) NOT NULL,

                            nivel INT NOT NULL,

                            vida INT NOT NULL,

                            mana INT NOT NULL,

                            id_usuario BIGINT NOT NULL,

                            id_clase_personaje BIGINT NOT NULL,

                            id_estado_personaje BIGINT NOT NULL,

                            CONSTRAINT fk_clase_personaje
                                FOREIGN KEY (id_clase_personaje)
                                    REFERENCES clases_personaje(id_clase_personaje),

                            CONSTRAINT fk_estado_personaje
                                FOREIGN KEY (id_estado_personaje)
                                    REFERENCES estados_personaje(id_estado_personaje)
);