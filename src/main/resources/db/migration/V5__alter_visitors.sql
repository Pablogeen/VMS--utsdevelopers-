ALTER TABLE visitors
    ADD COLUMN user_id BIGINT NOT NULL,

    ADD CONSTRAINT fk_visitors_user
        FOREIGN KEY (user_id)
        REFERENCES users(id);