USE TIENDA_BIBLIOTECA_DB;

-- =========================================================
-- 1. ACTUALIZACIÓN DE PORTADAS (Para los libros iniciales ID 1 y 2)
-- =========================================================
UPDATE LIBRO SET IMAGEN_PORTADA = 'https://images.unsplash.com/photo-1544947950-fa07a98d237f?q=80&w=300&auto=format&fit=crop' WHERE ISBN = '9788466361897'; -- Dune
UPDATE LIBRO SET IMAGEN_PORTADA = 'https://images.unsplash.com/photo-1512820790803-83ca734da794?q=80&w=300&auto=format&fit=crop' WHERE ISBN = '9788401344497'; -- Cosmos

-- =========================================================
-- 2. NUEVOS AUTORES (Evitando duplicar los existentes)
-- =========================================================
INSERT INTO AUTOR (NOMBRE) VALUES 
('STEPHEN KING'), 
('H.P. LOVECRAFT'), 
('NEIL GAIMAN'),
('HISTORIADOR GENERAL'); -- Agregado para el libro de historia

-- =========================================================
-- 3. NUEVAS CATEGORÍAS
-- =========================================================
INSERT INTO CATEGORIA (NOMBRE) VALUES 
('TERROR'), 
('FANTASIA'), 
('HISTORIA');

-- =========================================================
-- 4. NUEVOS LIBROS CON PORTADAS DE INTERNET REALES
-- =========================================================
INSERT INTO LIBRO (ISBN, TITULO, SLUG, IMAGEN_PORTADA, SINOPSIS, PAGINAS, FORMATO, PRECIO_VENTA_ACTUAL, PRECIO_ALQUILER_ACTUAL, EDITORIAL_ID, ACTIVO) VALUES
('9788497593793', 'EL RESPLANDOR', 'el-resplandor-stephen-king', 'https://images.unsplash.com/photo-1543002588-bfa74002ed7e?q=80&w=300&auto=format&fit=crop', 'Terror en un hotel aislado...', 500, 'TAPA_BLANDA', 89.90, 12.00, 1, TRUE),
('9788477027317', 'LORE CTHULHU', 'el-mito-de-cthulhu', 'https://images.unsplash.com/photo-1516979187457-637abb4f9353?q=80&w=300&auto=format&fit=crop', 'Los horrores cósmicos ancestrales...', 350, 'TAPA_DURA', 110.00, 18.00, 1, TRUE),
('9788498383393', 'CORALINE', 'coraline-neil-gaiman', 'https://images.unsplash.com/photo-1506880018603-83d5b814b5a6?q=80&w=300&auto=format&fit=crop', 'Cuidado con lo que deseas detrás de la puerta...', 210, 'DE_BOLSILLO', 55.00, 7.50, 2, TRUE),
('9786123164911', 'HISTORIA DEL PERU', 'historia-breve-del-peru', 'https://images.unsplash.com/photo-1532012197267-da84d127e765?q=80&w=300&auto=format&fit=crop', 'Un compendio resumido de nuestra era republicana...', 310, 'TAPA_BLANDA', 45.00, 0.00, 2, TRUE);

-- =========================================================
-- 5. VINCULACIÓN DINÁMICA DE RELACIONES (Usando Subconsultas Seguras)
-- =========================================================

-- Relaciones de Autores
INSERT INTO LIBRO_AUTOR (LIBRO_ID, AUTOR_ID) VALUES 
((SELECT ID FROM LIBRO WHERE ISBN = '9788497593793'), (SELECT ID FROM AUTOR WHERE NOMBRE = 'STEPHEN KING')),
((SELECT ID FROM LIBRO WHERE ISBN = '9788477027317'), (SELECT ID FROM AUTOR WHERE NOMBRE = 'H.P. LOVECRAFT')),
((SELECT ID FROM LIBRO WHERE ISBN = '9788498383393'), (SELECT ID FROM AUTOR WHERE NOMBRE = 'NEIL GAIMAN')),
((SELECT ID FROM LIBRO WHERE ISBN = '9786123164911'), (SELECT ID FROM AUTOR WHERE NOMBRE = 'HISTORIADOR GENERAL'));

-- Relaciones de Categorías
INSERT INTO LIBRO_CATEGORIA (LIBRO_ID, CATEGORIA_ID) VALUES 
((SELECT ID FROM LIBRO WHERE ISBN = '9788497593793'), (SELECT ID FROM CATEGORIA WHERE NOMBRE = 'TERROR')),
((SELECT ID FROM LIBRO WHERE ISBN = '9788477027317'), (SELECT ID FROM CATEGORIA WHERE NOMBRE = 'TERROR')),
((SELECT ID FROM LIBRO WHERE ISBN = '9788498383393'), (SELECT ID FROM CATEGORIA WHERE NOMBRE = 'FANTASIA')),
((SELECT ID FROM LIBRO WHERE ISBN = '9786123164911'), (SELECT ID FROM CATEGORIA WHERE NOMBRE = 'HISTORIA'));


ALTER TABLE USUARIO ADD COLUMN NRO_DOCUMENTO VARCHAR(15);
ALTER TABLE PEDIDO MODIFY COLUMN CANAL_VENTA VARCHAR(20) NULL;




-- 1. Quitamos temporalmente el modo seguro para aplicar los cambios de emergencia
SET SQL_SAFE_UPDATES = 0;

-- 2. Aseguramos que la columna ESTADO del historial acepte textos largos (le damos VARCHAR de 50)
ALTER TABLE historial_estado_pedido MODIFY COLUMN ESTADO VARCHAR(50) NOT NULL;

-- 3. Por si acaso, si la columna 'comentario' o 'notas' de esa misma tabla tiene límites cortos, la ampliamos
ALTER TABLE historial_estado_pedido MODIFY COLUMN comentario VARCHAR(255);

-- 4. De paso, eliminamos la restricción estricta de CANAL_VENTA en la tabla pedido que vimos antes
ALTER TABLE PEDIDO MODIFY COLUMN CANAL_VENTA VARCHAR(50) NULL;

-- 5. Volvemos a activar el modo seguro
SET SQL_SAFE_UPDATES = 1;