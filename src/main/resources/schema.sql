DROP TABLE IF EXISTS operation;
DROP TABLE IF EXISTS account;
DROP TABLE IF EXISTS contact;
DROP TABLE IF EXISTS unique_contact;
DROP TABLE IF EXISTS person;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS users;

DROP SEQUENCE IF EXISTS operation_seq;
DROP SEQUENCE IF EXISTS account_seq;
DROP SEQUENCE IF EXISTS contact_seq;
DROP SEQUENCE IF EXISTS unique_contact_seq;
DROP SEQUENCE IF EXISTS person_seq;
DROP SEQUENCE IF EXISTS users_seq;

DROP TYPE IF EXISTS operation_type;
DROP TYPE IF EXISTS account_type;
DROP TYPE IF EXISTS account_status;
DROP TYPE IF EXISTS contact_type;
DROP TYPE IF EXISTS user_role;
DROP TYPE IF EXISTS user_status;

CREATE TYPE user_status AS ENUM ('ACTIVE', 'INACTIVE');

CREATE TABLE users
(
    user_id    BIGINT PRIMARY KEY,
    first_name VARCHAR,
    last_name  VARCHAR,
    username   VARCHAR UNIQUE NOT NULL,
    email      VARCHAR UNIQUE NOT NULL,
    password   VARCHAR        NOT NULL,
    status     user_status    NOT NULL
);
CREATE SEQUENCE users_seq INCREMENT 10 START 20;
ALTER SEQUENCE users_seq OWNED BY users.user_id;
ALTER TABLE users
    ALTER COLUMN user_id SET DEFAULT nextval('users_seq');

CREATE TYPE user_role AS ENUM ('ADMIN', 'USER');

CREATE TABLE roles
(
    user_id BIGINT    NOT NULL REFERENCES users (user_id) ON DELETE CASCADE,
    role    user_role NOT NULL
);
ALTER TABLE roles
    ADD CONSTRAINT user_role_idx UNIQUE (user_id, role);

CREATE TABLE person
(
    person_id  BIGINT PRIMARY KEY,
    user_id    BIGINT REFERENCES users (user_id) ON DELETE CASCADE,
    first_name VARCHAR NOT NULL,
    last_name  VARCHAR,
    comment    VARCHAR
);
CREATE INDEX person_last_name_idx ON person (last_name);
CREATE SEQUENCE person_seq INCREMENT 10 START 20;
ALTER SEQUENCE person_seq OWNED BY person.person_id;
ALTER TABLE person
    ALTER COLUMN person_id SET DEFAULT nextval('person_seq');

CREATE TYPE contact_type AS ENUM ('PHONE', 'EMAIL');

CREATE TABLE contact
(
    contact_id BIGINT PRIMARY KEY,
    person_id  BIGINT REFERENCES person (person_id) ON DELETE CASCADE,
    type       contact_type NOT NULL,
    value      varchar      NOT NULL
);
CREATE SEQUENCE contact_seq INCREMENT 10 START 20;
ALTER SEQUENCE contact_seq OWNED BY contact.contact_id;
ALTER TABLE contact
    ALTER COLUMN contact_id SET DEFAULT nextval('contact_seq');

CREATE TABLE unique_contact
(
    unique_contact_id BIGINT PRIMARY KEY,
    user_id           BIGINT        NOT NULL,
    contact_id        BIGINT UNIQUE NOT NULL,
    contact_value     VARCHAR       NOT NULL,
    UNIQUE (user_id, contact_value)
);
CREATE SEQUENCE unique_contact_seq INCREMENT 10 START 20;
ALTER SEQUENCE unique_contact_seq OWNED BY unique_contact.unique_contact_id;
ALTER TABLE unique_contact
    ALTER COLUMN unique_contact_id SET DEFAULT nextval('unique_contact_seq');

CREATE TYPE account_type AS ENUM ('LEND', 'LOAN');
CREATE TYPE account_status AS ENUM ('ACTIVE', 'INACTIVE');

CREATE TABLE account
(
    account_id  BIGINT PRIMARY KEY,
    person_id   BIGINT REFERENCES person (person_id) ON DELETE CASCADE,
    type        account_type   NOT NULL,
    amount      NUMERIC(12, 2) NOT NULL DEFAULT 0.00,
    currency    VARCHAR        NOT NULL,
    rate        REAL           NOT NULL DEFAULT 0.0,
    open_date   TIMESTAMP      NOT NULL DEFAULT now(),
    closed_date TIMESTAMP,
    comment     VARCHAR,
    status      account_status NOT NULL
);
CREATE SEQUENCE account_seq INCREMENT 10 START 20;
ALTER SEQUENCE account_seq OWNED BY account.account_id;
ALTER TABLE account
    ALTER COLUMN account_id SET DEFAULT nextval('account_seq');

CREATE TYPE operation_type AS ENUM ('RECEIPT', 'EXPENSE');

CREATE TABLE operation
(
    operation_id BIGINT PRIMARY KEY,
    account_id   BIGINT REFERENCES account (account_id) ON DELETE CASCADE,
    type         operation_type NOT NULL,
    oper_date    TIMESTAMP      NOT NULL,
    amount       NUMERIC(12, 2) NOT NULL,
    description  VARCHAR
);
CREATE SEQUENCE operation_seq INCREMENT 10 START 20;
ALTER SEQUENCE operation_seq OWNED BY operation.operation_id;
ALTER TABLE operation
    ALTER COLUMN operation_id SET DEFAULT nextval('operation_seq');