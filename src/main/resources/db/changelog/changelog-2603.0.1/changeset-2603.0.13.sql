--liquibase formatted sql

--changeset rahmatullin.damir21@gmail.com:insert_system_badges_reference
ALTER TABLE badge
    ADD CONSTRAINT uk_badge_name UNIQUE (name);

INSERT INTO badge (name, description, required_points)
VALUES ('Новичок', 'Выдается всем новым пользователям при регистрации на сайте.', 0),
       ('Боллер', 'Особый статус для преданных игроков и покупателей.', 100),
       ('Знаток доставки', 'Выдается за активное использование службы доставки магазина.',
        50) ON CONFLICT (name) DO NOTHING;

--rollback ALTER TABLE badge DROP CONSTRAINT uk_badge_name;
--rollback DELETE FROM badge WHERE name IN ('Новичок', 'Боллер', 'Знаток доставки');