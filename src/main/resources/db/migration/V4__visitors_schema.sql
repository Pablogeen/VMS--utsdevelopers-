CREATE TABLE visitors (
                          id BIGINT NOT NULL AUTO_INCREMENT,
                          first_name VARCHAR(100) NOT NULL,
                          last_name VARCHAR(100) NOT NULL,
                          email VARCHAR(254) NOT NULL,
                          phone_number VARCHAR(30) NOT NULL,
                          address VARCHAR(500),
                          company VARCHAR(255),
                          purpose TEXT NOT NULL,
                          host_id BIGINT NOT NULL,
                          tag INT,
                          status VARCHAR(30) NOT NULL,
                          checked_in_time DATETIME(6),
                          checked_out_time DATETIME(6),

                          PRIMARY KEY (id),

                          CONSTRAINT fk_visitors_host
                              FOREIGN KEY (host_id)
                                  REFERENCES employees(id)
);