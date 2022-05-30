INSERT INTO users (user_id, name, email, password, status)
VALUES (1, 'admin', 'admin@email.ru', 'admin', 'ACTIVE'),
       (2, 'user', 'user@email.ru', 'user', 'ACTIVE');

INSERT INTO roles(user_id, role)
VALUES (1, 'ADMIN'),
       (1, 'USER'),
       (2, 'USER');

INSERT INTO person (person_id, first_name, last_name, comment)
VALUES (1, 'Ann', 'Jefferson', 'first person'),
       (2, 'Tom', 'Ballson', 'two person'),
       (3, 'Helen', 'Karlson', 'three person'),
       (4, 'Joan', 'Nikels', 'four person'),
       (5, 'Bob', 'Timerson', 'five person');

INSERT INTO contact(contact_id, person_id, type, value)
VALUES (1, 1, 'PHONE', '79181234567'),
       (2, 1, 'EMAIL', 'ann@mail.ru'),
       (3, 2, 'PHONE', '79181234568'),
       (4, 2, 'EMAIL', 'tom@mail.ru'),
       (5, 3, 'PHONE', '79181234569'),
       (6, 3, 'EMAIL', 'helen@mail.ru'),
       (7, 4, 'PHONE', '79181234578'),
       (8, 4, 'EMAIL', 'joan@mail.ru'),
       (9, 5, 'PHONE', '79181234579'),
       (10, 5, 'EMAIL', 'bob@mail.ru');

INSERT INTO account (account_id, person_id, type, amount, currency, rate, open_date, closed_date, comment, status)
VALUES (1, 1, 'LEND', 100.00, 'RUB', 0.0, '2022-05-01 10:23:54', null, 'lend to Ann', 'ACTIVE'),
       (2, 1, 'LOAN', 300.00, 'RUB', 0.0, '2022-05-02 11:01:25', null, 'loan from Ann', 'ACTIVE'),
       (3, 2, 'LEND', 500.00, 'RUB', 0.0, '2022-05-03 12:24:36', null, 'lend to Tom', 'ACTIVE'),
       (4, 2, 'LOAN', 200.00, 'RUB', 0.0, '2022-05-04 09:58:36', null, 'loan from Tom', 'ACTIVE'),
       (5, 3, 'LEND', 400.00, 'RUB', 0.0, '2022-05-06 14:03:36', null, 'lend to Helen', 'ACTIVE'),
       (6, 3, 'LOAN', 200.00, 'RUB', 0.0, '2022-05-07 15:11:36', null, 'loan from Helen', 'ACTIVE'),
       (7, 4, 'LEND', 400.00, 'RUB', 0.0, '2022-05-08 17:35:36', null, 'lend to Joan', 'ACTIVE'),
       (8, 4, 'LOAN', 300.00, 'RUB', 0.0, '2022-05-09 18:47:36', null, 'loan from Joan', 'ACTIVE'),
       (9, 5, 'LEND', 600.00, 'RUB', 0.0, '2022-05-10 08:32:36', null, 'lend to Bob', 'ACTIVE'),
       (10, 5, 'LOAN', 300.00, 'RUB', 0.0, '2022-05-10 16:37:36', null, 'loan from Bob', 'ACTIVE'),
       (11, 1, 'LOAN', 333.00, 'RUB', 0.0, '2022-04-14 04:44:44', null, 'inactive loan account from Ann', 'INACTIVE'),
       (12, 5, 'LOAN', 444.00, 'RUB', 0.0, '2022-04-15 05:55:55', null, 'inactive loan account from Bob', 'INACTIVE'),
       (13, 3, 'LOAN', 555.00, 'RUB', 0.0, '2022-04-16 07:07:07', null, 'inactive loan account from Helen', 'INACTIVE');

INSERT INTO operation (operation_id, account_id, type, oper_date, amount, description)
VALUES (1, 1, 'EXPENSE', '2022-05-01 10:23:54', 100.00, 'lend to Ann'),
       (2, 2, 'RECEIPT', '2022-05-02 11:01:25', 300.00, 'loan from Ann'),
       (3, 3, 'EXPENSE', '2022-05-03 12:24:36', 500.00, 'lend to Tom'),
       (4, 4, 'RECEIPT', '2022-05-04 09:58:36', 200.00, 'loan from Tom'),
       (5, 5, 'EXPENSE', '2022-05-06 14:03:36', 400.00, 'lend to Helen'),
       (6, 6, 'RECEIPT', '2022-05-07 15:11:36', 200.00, 'loan from Helen'),
       (7, 7, 'EXPENSE', '2022-05-08 17:35:36', 400.00, 'lend to Joan'),
       (8, 8, 'RECEIPT', '2022-05-09 18:47:36', 300.00, 'loan from Joan'),
       (9, 9, 'EXPENSE', '2022-05-10 08:32:36', 600.00, 'lend to Bob'),
       (10, 10, 'RECEIPT', '2022-05-10 16:37:36', 300.00, 'loan from Bob');