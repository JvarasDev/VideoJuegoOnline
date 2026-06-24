ALTER TABLE items_inventario ADD COLUMN nombre_item VARCHAR(100);

-- Actualizar los items iniciales para que no sean nulos
UPDATE items_inventario SET nombre_item = 'Espada Básica' WHERE id_referencia_item = 1;
UPDATE items_inventario SET nombre_item = 'Arco Básico' WHERE id_referencia_item = 2;
UPDATE items_inventario SET nombre_item = 'Hacha Básica' WHERE id_referencia_item = 3;
UPDATE items_inventario SET nombre_item = 'Báculo Básico' WHERE id_referencia_item = 4;
UPDATE items_inventario SET nombre_item = 'Daga Básica' WHERE id_referencia_item = 5;
UPDATE items_inventario SET nombre_item = 'Item Desconocido' WHERE nombre_item IS NULL;

-- Aplicar la restricción NOT NULL de vuelta
ALTER TABLE items_inventario MODIFY COLUMN nombre_item VARCHAR(100) NOT NULL;
