-- Добавление пользователя admin с паролем admin
INSERT INTO users (username, email, password, role, birth_date, gender) 
VALUES ('admin', 
        'admin@mail.ru', 
        '$2a$12$94myWgI693.i9wyF4q20MuZUs4QZ1REzej6wJAxKsK5sVdGCqh48q',
        2,
        '2002-04-21', 
        'Мужчина');