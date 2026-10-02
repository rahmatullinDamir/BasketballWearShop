--liquibase formatted sql
--changeset rahmatullin.damir21@gmail.com:create_cart_table

DROP TABLE IF EXISTS cart;

CREATE TABLE cart(
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT UNIQUE NOT NULL,
    applied_certificate_id BIGINT,

    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (applied_certificate_id) REFERENCES gift_certificate(id) ON DELETE SET NULL
);

--rollback DROP TABLE cart;