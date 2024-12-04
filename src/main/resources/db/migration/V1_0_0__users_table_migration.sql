-- Создание таблицы
CREATE TABLE IF NOT EXISTS users (
    id BIGINT NOT NULL AUTO_INCREMENT,        -- Автоинкремент для уникальных идентификаторов
    username VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    role SMALLINT NOT NULL,
    is_enabled BOOLEAN NOT NULL DEFAULT TRUE, -- Поле для включения/отключения пользователя
    birth_date DATE,                          -- Поле даты рождения для опрпделения знака зодиака
    gender VARCHAR(10),                       -- Например, 'Мужчина', 'Женщина'
    PRIMARY KEY (id),                         -- Первичный ключ
    UNIQUE KEY (email),                       -- Уникальный индекс на email
    UNIQUE KEY (username)                     -- Уникальный индекс на username
);