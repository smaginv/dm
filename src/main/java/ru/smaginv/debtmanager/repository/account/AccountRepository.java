package ru.smaginv.debtmanager.repository.account;

import ru.smaginv.debtmanager.entity.account.Account;
import ru.smaginv.debtmanager.entity.account.AccountStatus;
import ru.smaginv.debtmanager.entity.account.AccountType;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AccountRepository {

    Optional<Account> get(Long accountId);

    List<Account> getAll();

    List<Account> getAllByPerson(Long personId);

    List<Account> getAllByStatus(AccountStatus accountStatus);

    List<Account> getAllByType(AccountType accountType);

    BigDecimal getActiveAccountsTotalAmountByType(AccountType accountType);

    BigDecimal getInactiveAccountsTotalAmountByType(AccountType accountType);

    void update(Account account);

    Account create(Long personId, Account account);

    void save(Account account);

    int delete(Long accountId);

    int deleteAllInactiveByPerson(Long personId);

    int deleteAllInactive();
}
