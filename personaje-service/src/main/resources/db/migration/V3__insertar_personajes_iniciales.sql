INSERT INTO personajes
(nombre, nivel, vida, mana, id_usuario, id_clase_personaje, id_estado_personaje)
SELECT 'GuerreraLuz', 1, 100, 50, 1, 1, 1
    WHERE NOT EXISTS (
    SELECT 1 FROM personajes WHERE nombre = 'GuerreraLuz'
);

INSERT INTO personajes
(nombre, nivel, vida, mana, id_usuario, id_clase_personaje, id_estado_personaje)
SELECT 'MagoAzul', 2, 80, 120, 3, 2, 1
    WHERE NOT EXISTS (
    SELECT 1 FROM personajes WHERE nombre = 'MagoAzul'
);

INSERT INTO personajes
(nombre, nivel, vida, mana, id_usuario, id_clase_personaje, id_estado_personaje)
SELECT 'ArqueraNorte', 3, 90, 70, 4, 3, 1
    WHERE NOT EXISTS (
    SELECT 1 FROM personajes WHERE nombre = 'ArqueraNorte'
);

INSERT INTO personajes
(nombre, nivel, vida, mana, id_usuario, id_clase_personaje, id_estado_personaje)
SELECT 'TanqueRojo', 4, 150, 40, 5, 1, 1
    WHERE NOT EXISTS (
    SELECT 1 FROM personajes WHERE nombre = 'TanqueRojo'
);

INSERT INTO personajes
(nombre, nivel, vida, mana, id_usuario, id_clase_personaje, id_estado_personaje)
SELECT 'HechiceraVIP', 5, 85, 150, 6, 2, 1
    WHERE NOT EXISTS (
    SELECT 1 FROM personajes WHERE nombre = 'HechiceraVIP'
);

INSERT INTO personajes
(nombre, nivel, vida, mana, id_usuario, id_clase_personaje, id_estado_personaje)
SELECT 'FlechaDorada', 6, 95, 80, 7, 3, 1
    WHERE NOT EXISTS (
    SELECT 1 FROM personajes WHERE nombre = 'FlechaDorada'
);

INSERT INTO personajes
(nombre, nivel, vida, mana, id_usuario, id_clase_personaje, id_estado_personaje)
SELECT 'NovatoUno', 1, 100, 50, 8, 1, 1
    WHERE NOT EXISTS (
    SELECT 1 FROM personajes WHERE nombre = 'NovatoUno'
);

INSERT INTO personajes
(nombre, nivel, vida, mana, id_usuario, id_clase_personaje, id_estado_personaje)
SELECT 'SombraOscura', 2, 80, 110, 9, 2, 1
    WHERE NOT EXISTS (
    SELECT 1 FROM personajes WHERE nombre = 'SombraOscura'
);

INSERT INTO personajes
(nombre, nivel, vida, mana, id_usuario, id_clase_personaje, id_estado_personaje)
SELECT 'CaballeroSur', 3, 130, 45, 10, 1, 1
    WHERE NOT EXISTS (
    SELECT 1 FROM personajes WHERE nombre = 'CaballeroSur'
);

INSERT INTO personajes
(nombre, nivel, vida, mana, id_usuario, id_clase_personaje, id_estado_personaje)
SELECT 'ArqueraFinal', 4, 90, 85, 2, 3, 1
    WHERE NOT EXISTS (
    SELECT 1 FROM personajes WHERE nombre = 'ArqueraFinal'
);