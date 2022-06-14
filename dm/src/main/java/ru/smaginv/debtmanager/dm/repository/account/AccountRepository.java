package ru.smaginv.debtmanager.dm.repository.account;

import ru.smaginv.debtmanager.dm.entity.account.Account;
import ru.smaginv.debtmanager.dm.entity.account.AccountStatus;
import ru.smaginv.debtmanager.dm.entity.account.AccountType;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AccountRepository {

    Optional<Account> get(Long userId, Long accountId);

    List<Account> getAll(Long userId);

    List<Account> getAllByPerson(Long userId, Long personId);

    List<Account> getAllByStatus(Long userId, AccountStatus accountStatus);

    List<Account> getAllByType(Long userId, AccountType accountType);

    BigDecimal getActiveAccountsTotalAmountByType(Long userId, AccountType accountType);

    BigDecimal getInactiveAccountsTotalAmountByType(Long userId, AccountType accountType);

    void update(Account account);

    Account create(Long userId, Long personId, Account account);

    void save(Account account);

    int delete(Long userId, Long accountId);

    int deleteAllInactiveByPerson(Long userId, Long personId);

    int deleteAllInactive(Long userId);
}
