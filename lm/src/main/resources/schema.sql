DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS log;

DROP SEQUENCE IF EXISTS users_seq;
DROP SEQUENCE IF EXISTS log_seq;

DROP TYPE IF EXISTS user_role;
DROP TYPE IF EXISTS http_method;

CREATE TYPE user_role AS ENUM ('ADMIN', 'USER');

CREATE TABLE users
(
    user_id  BIGINT PRIMARY KEY,
    username VARCHAR UNIQUE NOT NULL,
    email    VARCHAR UNIQUE NOT NULL,
    password VARCHAR        NOT NULL,
    role     user_role      NOT NULL
);
ALTER TABLE users
    ADD CONSTRAINT user_role_idx UNIQUE (username, role);

CREATE SEQUENCE users_seq INCREMENT 10 START 20;
ALTER SEQUENCE users_seq OWNED BY users.user_id;
ALTER TABLE users
    ALTER COLUMN user_id SET DEFAULT nextval('users_seq');

CREATE TYPE http_method AS ENUM ('GET', 'HEAD', 'POST', 'PUT', 'PATCH', 'DELETE', 'OPTIONS', 'TRACE');

CREATE TABLE log
(
    log_id        BIGINT PRIMARY KEY,
    timestamp     TIMESTAMP   NOT NULL,
    username      VARCHAR     NOT NULL,
    request_uri   VARCHAR     NOT NULL,
    method        http_method NOT NULL,
    request_body  jsonb       NOT NULL,
    response_body jsonb       NOT NULL
);

CREATE SEQUENCE log_seq INCREMENT 10 START 1;
ALTER SEQUENCE log_seq OWNED BY log.log_id;
ALTER TABLE log
    ALTER COLUMN log_id SET DEFAULT nextval('log_seq');