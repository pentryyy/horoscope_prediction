CREATE TABLE IF NOT EXISTS users (
    id bigint NOT NULL,
    username CHAR(50) NOT NULL UNIQUE,
    password CHAR(100) NOT NULL,
    email CHAR(100) NOT NULL UNIQUE,
    role smallint NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY (id)
)