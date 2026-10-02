--liquibase formatted sql
--changeset rahmatullin.damir21@gmail.com:create_table_orders

DROP TABLE IF EXISTS orders;

CREATE TABLE orders
(
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT,
    created_at TIMESTAMP,
    total NUMERIC,
    discount INT,

    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

--rollback DROP TABLE orders;