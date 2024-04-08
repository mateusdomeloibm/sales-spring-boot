CREATE TABLE client(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100)
);

CREATE TABLE product(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    description VARCHAR(255),
    sku VARCHAR(25) UNIQUE,
    price NUMERIC(20, 2)
);

CREATE TABLE `order`(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    client_id INTEGER REFERENCES client(id),
    total NUMERIC(20, 2)
);

CREATE TABLE order_item(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    product_id INTEGER REFERENCES product(id),
    name VARCHAR(100),
    sku VARCHAR(25) REFERENCES product(sku),
    order_id INTEGER REFERENCES `order`(id),
    quantity INTEGER,
    price NUMERIC(20, 2)
);

CREATE TABLE user (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50),
    password VARCHAR(255),
    admin BOOLEAN DEFAULT FALSE
)