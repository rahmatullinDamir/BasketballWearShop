--liquibase formatted sql
--changeset rahmatullin.damir21@gmail.com:create_table_gift_certificate

DROP TABLE IF EXISTS gift_certificate;

CREATE TABLE gift_certificate
(
    id BIGSERIAL PRIMARY KEY,
    amount NUMERIC NOT NULL,
    code VARCHAR(255) UNIQUE NOT NULL,
    created_at TIMESTAMP NOT NULL,
    used_at TIMESTAMP,
    expires_at TIMESTAMP NOT NULL,
    buyer_id BIGINT,
    used_by_id BIGINT,
    status VARCHAR(255) NOT NULL,

    FOREIGN KEY (buyer_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (used_by_id) REFERENCES users(id) ON DELETE CASCADE
);

--rollback DROP TABLE gift_certificate;