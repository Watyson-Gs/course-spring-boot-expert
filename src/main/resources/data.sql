CREATE TABLE produto (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL,
    descricao VARCHAR(255),
    preco numeric(16, 4)
);

INSERT INTO produto (nome, descricao, preco) VALUES
    ('Camiseta Branca', 'Camiseta de algodão tamanho M', 49.90),
    ('Notebook Dell XPS', 'Notebook com 16GB RAM e SSD 512GB', 7599.00),
    ('Fone de Ouvido Bluetooth', 'Fone com cancelamento de ruído', 299.99),
    ('Café Especial', 'Pacote de 500g de café arábica premium', 34.50),
    ('Smartphone Samsung Galaxy', 'Celular com 128GB de armazenamento', 2499.00),
    ('Monitor LG 27"', 'Monitor IPS Full HD', 1199.99),
    ('Teclado Mecânico', 'Teclado mecânico com switches azuis', 379.90),
    ('Mouse Gamer', 'Mouse óptico com RGB e 16000 DPI', 219.00),
    ('Mochila para Notebook', 'Mochila resistente à água 15.6"', 149.99),
    ('Impressora Multifuncional', 'Impressora jato de tinta com scanner', 499.90),
    ('Cadeira Ergonômica', 'Cadeira para escritório com apoio lombar', 899.00),
    ('HD Externo 1TB', 'HD USB 3.0 portátil', 349.00),
    ('SSD NVMe 1TB', 'SSD de alta velocidade M.2', 599.99),
    ('Tablet Samsung Galaxy Tab', 'Tela de 10.5" e 64GB de armazenamento', 1899.00),
    ('Smartwatch Amazfit', 'Relógio inteligente com monitoramento cardíaco', 649.90),
    ('Carregador Portátil', 'Power bank 20.000mAh com carga rápida', 179.00),
    ('Roteador Wi-Fi 6', 'Roteador de alta velocidade para jogos', 429.00),
    ('Webcam Full HD', 'Câmera para videochamadas e streaming', 229.00),
    ('Hub USB-C', 'Adaptador com 5 portas', 139.00),
    ('Luminária LED de Mesa', 'Com regulagem de brilho e temperatura', 89.90);