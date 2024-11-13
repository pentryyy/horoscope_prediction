-- Constraint: roles_rolename_key

-- ALTER TABLE IF EXISTS for_horoscope_db.roles DROP CONSTRAINT IF EXISTS roles_rolename_key;

ALTER TABLE IF EXISTS for_horoscope_db.roles
    ADD CONSTRAINT roles_rolename_key UNIQUE (rolename);