DROP TABLE IF EXISTS log;
DROP SEQUENCE IF EXISTS log_seq;
DROP TYPE IF EXISTS http_method;

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