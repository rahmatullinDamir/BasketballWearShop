--liquibase formatted sql
--changeset rahmatullin.damir21@gmail.com:create_table_product

DROP TABLE IF EXISTS product;

CREATE TABLE product
(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    description VARCHAR(255),
    price NUMERIC
);

--rollback DROP TABLE product;