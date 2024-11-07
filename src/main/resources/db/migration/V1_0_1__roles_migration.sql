-- Table: for_horoscope_db.roles

-- DROP TABLE IF EXISTS for_horoscope_db.roles;

CREATE TABLE IF NOT EXISTS for_horoscope_db.roles
(
    id smallint NOT NULL,
    rolename character(50) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT roles_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS for_horoscope_db.roles
    OWNER to postgres;