CREATE TABLE employees (
                           id BIGINT NOT NULL AUTO_INCREMENT,
                           first_name VARCHAR(100) NOT NULL,
                           last_name VARCHAR(100) NOT NULL,
                           email VARCHAR(254) NOT NULL,
                           phone_number VARCHAR(30) NOT NULL,
                           department VARCHAR(100) NOT NULL,

                           PRIMARY KEY (id),
                           UNIQUE KEY uk_employees_email (email)
);