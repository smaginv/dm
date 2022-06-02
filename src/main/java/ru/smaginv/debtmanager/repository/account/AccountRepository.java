package ru.smaginv.debtmanager.repository.account;

import ru.smaginv.debtmanager.entity.account.Account;
import ru.smaginv.debtmanager.entity.account.AccountStatus;
import ru.smaginv.debtmanager.entity.account.AccountType;

import java.util.List;
import java.util.Optional;

public interface AccountRepository {

    Optional<Account> get(Long personId, Long accountId);

    Optional<Account> getById(Long accountId);

    List<Account> getAll();

    List<Account> getAllByPerson(Long personId);

    List<Account> getByStatus(Long personId, AccountStatus accountStatus);

    List<Account> getAllByType(AccountType accountType);

    Account save(Long personId, Account account);

    void save(Account account);

    int delete(Long personId, Long accountId);

    int deleteAllInactiveByPerson(Long personId);

    int deleteAllInactive();
}
