-- Создание таблицы
CREATE TABLE IF NOT EXISTS users (
    id BIGINT NOT NULL AUTO_INCREMENT, -- Автоинкремент для уникальных идентификаторов
    username VARCHAR(50) NOT NULL,    -- Замена 'character(50)' на 'VARCHAR(50)'
    password VARCHAR(100) NOT NULL,   -- Замена 'character(100)' на 'VARCHAR(100)'
    email VARCHAR(100) NOT NULL,      -- Замена 'character(100)' на 'VARCHAR(100)'
    role SMALLINT NOT NULL,
    PRIMARY KEY (id),                 -- Первичный ключ
    UNIQUE KEY (email),               -- Уникальный индекс на email
    UNIQUE KEY (username)             -- Уникальный индекс на username
);