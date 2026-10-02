--liquibase formatted sql
--changeset rahmatullin.damir21@gmail.com:create_table_badge

DROP TABLE IF EXISTS badge;

CREATE TABLE badge(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    description VARCHAR(255),
    required_points INT,
    icon_image_info_id BIGINT UNIQUE,

    FOREIGN KEY (icon_image_info_id) REFERENCES image_info(id) ON DELETE CASCADE
);

--rollback DROP TABLE badge;