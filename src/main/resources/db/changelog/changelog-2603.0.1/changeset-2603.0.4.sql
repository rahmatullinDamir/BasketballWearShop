--liquibase formatted sql
--changeset rahmatullin.damir21@gmail.com:create_table_cart_item

DROP TABLE IF EXISTS cart_item;

CREATE TABLE cart_item(
    id BIGSERIAL PRIMARY KEY,
    quantity int NOT NULL,
    product_id BIGINT NOT NULL,
    product_size_id BIGINT NOT NULL,
    cart_id BIGINT NOT NULL,

    FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE,
    FOREIGN KEY (cart_id) REFERENCES cart(id) ON DELETE CASCADE,
    FOREIGN KEY (product_size_id) REFERENCES product_sizes (id) ON DELETE CASCADE
);

--rollback DROP TABLE cart_item;