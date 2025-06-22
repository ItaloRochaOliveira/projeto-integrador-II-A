CREATE DATABASE IF NOT EXISTS user_db;

USE user_db;

CREATE TABLE phonebook (
    id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    user_id INT NOT NULL,
    name VARCHAR(100) NOT NULL,
    telefone VARCHAR(20) NOT NULL,
    email VARCHAR(100) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

INSERT INTO phonebook (user_id, name, telefone, email) VALUES
(1, 'Contato 1 do User 1', '11999999999', 'contato1_user1@email.com'),
(1, 'Contato 2 do User 1', '11888888888', 'contato2_user1@email.com'),
(2, 'Contato 1 do User 2', '11777777777', 'contato1_user2@email.com');
