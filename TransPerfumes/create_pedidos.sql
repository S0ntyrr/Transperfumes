CREATE TABLE IF NOT EXISTS pedidos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    identificacion VARCHAR(20),
    direccion VARCHAR(150),
    ciudad VARCHAR(100),
    pais VARCHAR(50),
    perfumes TEXT
);
