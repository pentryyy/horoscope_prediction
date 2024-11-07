-- Table: for_horoscope_db.users

-- DROP TABLE IF EXISTS for_horoscope_db.users;

CREATE TABLE IF NOT EXISTS for_horoscope_db.users
(
    id bigint NOT NULL,
    username character(50) COLLATE pg_catalog."default" NOT NULL,
    password character(100) COLLATE pg_catalog."default" NOT NULL,
    email character(100) COLLATE pg_catalog."default" NOT NULL,
    role smallint NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY (id),
    CONSTRAINT users_email_key UNIQUE (email),
    CONSTRAINT users_username_key UNIQUE (username)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS for_horoscope_db.users
    OWNER to postgres;