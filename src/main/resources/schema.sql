DROP TABLE IF EXISTS operation;
DROP TABLE IF EXISTS account;
DROP TABLE IF EXISTS person;

DROP SEQUENCE IF EXISTS operation_seq;
DROP SEQUENCE IF EXISTS account_seq;
DROP SEQUENCE IF EXISTS person_seq;

DROP TYPE IF EXISTS operation_type;
DROP TYPE IF EXISTS account_type;

CREATE TABLE person
(
    person_id    BIGINT PRIMARY KEY,
    first_name   VARCHAR NOT NULL,
    last_name    VARCHAR,
    phone_number VARCHAR NOT NULL UNIQUE,
    email        VARCHAR UNIQUE,
    comment      VARCHAR
);
CREATE INDEX person_last_name_idx ON person (last_name);
CREATE SEQUENCE person_seq INCREMENT 10 START 20;
ALTER SEQUENCE person_seq OWNED BY person.person_id;
ALTER TABLE person
    ALTER COLUMN person_id SET DEFAULT nextval('person_seq');

CREATE TYPE account_type AS ENUM ('DEBIT', 'CREDIT');

CREATE TABLE account
(
    account_id  BIGINT PRIMARY KEY,
    person_id   BIGINT REFERENCES person (person_id) ON DELETE CASCADE,
    type        account_type,
    amount      NUMERIC(12, 2) NOT NULL DEFAULT 0.00,
    currency    VARCHAR        NOT NULL,
    rate        REAL           NOT NULL DEFAULT 0.0,
    open_date   TIMESTAMP      NOT NULL DEFAULT now(),
    closed_date TIMESTAMP,
    comment     VARCHAR,
    is_active   BOOLEAN        NOT NULL DEFAULT true
);
CREATE SEQUENCE account_seq INCREMENT 10 START 20;
ALTER SEQUENCE account_seq OWNED BY account.account_id;
ALTER TABLE account
    ALTER COLUMN account_id SET DEFAULT nextval('account_seq');

CREATE TYPE operation_type AS ENUM ('LEND', 'LOAN');

CREATE TABLE operation
(
    operation_id BIGINT PRIMARY KEY,
    account_id   BIGINT REFERENCES account (account_id) ON DELETE CASCADE,
    type         operation_type,
    oper_date    TIMESTAMP      NOT NULL,
    amount       NUMERIC(12, 2) NOT NULL,
    description  VARCHAR
);
CREATE SEQUENCE operation_seq INCREMENT 10 START 20;
ALTER SEQUENCE operation_seq OWNED BY operation.operation_id;
ALTER TABLE operation
    ALTER COLUMN operation_id SET DEFAULT nextval('operation_seq');