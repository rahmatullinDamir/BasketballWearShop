--liquibase formatted sql
--changeset rahmatullin.damir21@gmail.com:create_table_address

DROP TABLE IF EXISTS address;

CREATE TABLE address(
    id BIGSERIAL PRIMARY KEY,
    street VARCHAR(255),
    city VARCHAR(255),
    postal_code VARCHAR(255),
    user_id BIGINT UNIQUE,
    country VARCHAR(255),

    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

--rollback DROP TABLE address;