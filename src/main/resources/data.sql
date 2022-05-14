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

INSERT INTO account (account_id, person_id, type, amount, currency, rate, open_date, closed_date, comment, is_active)
VALUES (1, 1, 'DEBIT', 100.00, 'RUB', 0.0, '2022-05-01 10:23:54', null, 'debit account for Ann', true),
       (2, 1, 'CREDIT', 300.00, 'RUB', 0.0, '2022-05-02 11:01:25', null, 'credit account for Ann', true),
       (3, 2, 'DEBIT', 500.00, 'RUB', 0.0, '2022-05-03 12:24:36', null, 'debit account for Tom', true),
       (4, 2, 'CREDIT', 200.00, 'RUB', 0.0, '2022-05-04 09:58:36', null, 'credit account for Tom', true),
       (5, 3, 'DEBIT', 400.00, 'RUB', 0.0, '2022-05-06 14:03:36', null, 'debit account for Helen', true),
       (6, 3, 'CREDIT', 200.00, 'RUB', 0.0, '2022-05-07 15:11:36', null, 'credit account for Helen', true),
       (7, 4, 'DEBIT', 400.00, 'RUB', 0.0, '2022-05-08 17:35:36', null, 'debit account for Joan', true),
       (8, 4, 'CREDIT', 300.00, 'RUB', 0.0, '2022-05-09 18:47:36', null, 'credit account for Joan', true),
       (9, 5, 'DEBIT', 600.00, 'RUB', 0.0, '2022-05-10 08:32:36', null, 'debit account for Bob', true),
       (10, 5, 'CREDIT', 300.00, 'RUB', 0.0, '2022-05-10 16:37:36', null, 'credit account for Bob', true);

INSERT INTO operation (operation_id, account_id, type, oper_date, amount, description)
VALUES (1, 1, 'LEND', '2022-05-01 10:23:54', 100.00, 'lend to Ann'),
       (2, 2, 'LOAN', '2022-05-02 11:01:25', 300.00, 'loan to Ann'),
       (3, 3, 'LEND', '2022-05-03 12:24:36', 500.00, 'lend to Tom'),
       (4, 4, 'LOAN', '2022-05-04 09:58:36', 200.00, 'loan to Tom'),
       (5, 5, 'LEND', '2022-05-06 14:03:36', 400.00, 'lend to Helen'),
       (6, 6, 'LOAN', '2022-05-07 15:11:36', 200.00, 'loan to Helen'),
       (7, 7, 'LEND', '2022-05-08 17:35:36', 400.00, 'lend to Joan'),
       (8, 8, 'LOAN', '2022-05-09 18:47:36', 300.00, 'loan to Joan'),
       (9, 9, 'LEND', '2022-05-10 08:32:36', 600.00, 'lend to Bob'),
       (10, 10, 'LOAN', '2022-05-10 16:37:36', 300.00, 'loan to Bob');