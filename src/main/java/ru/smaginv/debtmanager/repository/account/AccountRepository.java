package ru.smaginv.debtmanager.repository.account;

import ru.smaginv.debtmanager.entity.account.Account;

import java.util.List;
import java.util.Optional;

public interface AccountRepository {

    Optional<Account> get(Long accountId, Long personId);

    Optional<Account> getWithOperations(Long accountId, Long personId);

    List<Account> getAll();

    List<Account> getAllWithOperations();

    List<Account> getAllByPerson(Long personId);

    List<Account> getAllActive();

    List<Account> getAllActiveByPerson(Long personId);

    List<Account> getAllInactive();

    List<Account> getAllInactiveByPerson(Long personId);

    List<Account> getAllDebit();

    List<Account> getAllCredit();

    Account save(Account account, Long personId);

    int delete(Long accountId, Long personId);

    int deleteAllByPerson(Long personId);

    int deleteAllInactiveByPerson(Long personId);

    int deleteAllInactive();
}
