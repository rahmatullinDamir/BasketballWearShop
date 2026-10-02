--liquibase formatted sql
--changeset rahmatullin.damir21@gmail.com:create_table_image_info

DROP TABLE IF EXISTS image_info;

CREATE TABLE image_info
(
    id BIGSERIAL PRIMARY KEY,
    original_name VARCHAR(255),
    storage_name VARCHAR(255),
    content_type VARCHAR(255),
    size BIGINT,
    product_id BIGINT,

    FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE
);

--rollback DROP TABLE image_info;