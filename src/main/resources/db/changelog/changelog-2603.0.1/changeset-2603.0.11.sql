--liquibase formatted sql
--changeset rahmatullin.damir21@gmail.com:create_users_table

DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    role VARCHAR(255)
);

--rollback DROP TABLE users;