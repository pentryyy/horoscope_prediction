CREATE TABLE IF NOT EXISTS roles (
    id SMALLINT NOT NULL,          -- Идентификатор
    rolename VARCHAR(50) NOT NULL, -- Название роли, VARCHAR заменяет character
    PRIMARY KEY (id),              -- Первичный ключ
    UNIQUE KEY (rolename)          -- Уникальный индекс для rolename
);