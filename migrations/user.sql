CREATE DATABASE IF NOT EXISTS user_db;

USE user_db;

CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL
);

INSERT INTO users (id, name, email) 
VALUES (1, 'user1', 'email1@gmail.com'), (2, 'user2', 'email2@gmail.com');