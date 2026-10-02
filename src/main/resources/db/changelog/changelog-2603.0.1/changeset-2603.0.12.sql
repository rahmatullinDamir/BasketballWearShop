--liquibase formatted sql
--changeset rahmatullin.damir21@gmail.com:create_table_user_badge

DROP TABLE IF EXISTS user_badge;

CREATE TABLE user_badge
(
    user_id BIGINT NOT NULL,
    badge_id BIGINT NOT NULL,

    PRIMARY KEY (user_id, badge_id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (badge_id) REFERENCES badge (id) ON DELETE CASCADE
);

--rollback DROP TABLE user_badge;