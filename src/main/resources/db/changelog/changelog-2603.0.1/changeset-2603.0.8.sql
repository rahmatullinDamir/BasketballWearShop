--liquibase formatted sql
--changeset rahmatullin.damir21@gmail.com:create_table_order_items

DROP TABLE IF EXISTS order_items;

CREATE TABLE order_items
(
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT,
    product_id BIGINT,
    quantity INT,
    size VARCHAR(255),
    price NUMERIC,

    FOREIGN KEY (order_id) REFERENCES orders (id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE CASCADE
);

--rollback DROP TABLE order_items;