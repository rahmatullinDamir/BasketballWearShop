--liquibase formatted sql
--changeset rahmatullin.damir21@gmail.com:create_product_sizes_table

DROP TABLE IF EXISTS product_sizes;

CREATE TABLE product_sizes
(
    id BIGSERIAL PRIMARY KEY,
    size VARCHAR(255) NOT NULL,
    stock INT,
    price NUMERIC,
    product_id BIGINT NOT NULL,

    FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE
);

--rollback DROP TABLE product_sizes;