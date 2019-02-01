DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS customers;

-- Table: users
CREATE TABLE customers(
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);
-- Table: roles
CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Table for mapping users and roles: user_roles
CREATE TABLE user_roles (
    customer_id INT NOT NULL,
    role_id INT NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customers(id),
    FOREIGN KEY (role_id) REFERENCES roles(id),
    UNIQUE (customer_id, role_id)
);

-- Insert data

INSERT INTO customers VALUES (1, 'admin', 'admin');

INSERT INTO roles VALUES (1, 'ROLE_USER');
INSERT INTO roles VALUES (2, 'ROLE_ADMIN');

INSERT INTO user_roles VALUES (1, 2);