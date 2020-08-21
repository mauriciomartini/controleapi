CREATE TABLE categoria(
    codigo SERIAL,
    nome VARCHAR(50) NOT NULL
);

INSERT INTO categoria (nome) values ('Imposto');
INSERT INTO categoria (nome) values ('Casa');
INSERT INTO categoria (nome) values ('Mercado');