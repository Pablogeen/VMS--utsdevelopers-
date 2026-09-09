CREATE TABLE users (
                       id BIGINT NOT NULL AUTO_INCREMENT,
                       email VARCHAR(50),
                       password VARCHAR(255),
                       role VARCHAR(50),
                       created_at DATETIME,

                       PRIMARY KEY (id)
);

CREATE UNIQUE INDEX uk_users_email
    ON users(email);