package ru.smaginv.debtmanager.repository.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.smaginv.debtmanager.entity.account.Account;
import ru.smaginv.debtmanager.entity.account.AccountStatus;
import ru.smaginv.debtmanager.entity.account.AccountType;
import ru.smaginv.debtmanager.entity.person.Person;
import ru.smaginv.debtmanager.repository.person.PersonRepositoryJpa;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static ru.smaginv.debtmanager.util.entity.EntityUtil.getEntityFromOptional;

@Repository
public class AccountRepositoryImpl implements AccountRepository {

    private final AccountRepositoryJpa accountRepository;
    private final PersonRepositoryJpa personRepository;

    @Autowired
    public AccountRepositoryImpl(AccountRepositoryJpa accountRepository, PersonRepositoryJpa personRepository) {
        this.accountRepository = accountRepository;
        this.personRepository = personRepository;
    }

    @Override
    public Optional<Account> get(Long userId, Long accountId) {
        return accountRepository.get(userId, accountId);
    }

    @Override
    public List<Account> getAll(Long userId) {
        return accountRepository.getAll(userId);
    }

    @Override
    public List<Account> getAllByPerson(Long userId, Long personId) {
        return accountRepository.getAllByPerson(userId, personId);
    }

    @Override
    public List<Account> getAllByStatus(Long userId, AccountStatus accountStatus) {
        return accountRepository.getAllByStatus(userId, accountStatus);
    }

    @Override
    public List<Account> getAllByType(Long userId, AccountType accountType) {
        return accountRepository.getAllByType(userId, accountType);
    }

    @Override
    public BigDecimal getActiveAccountsTotalAmountByType(Long userId, AccountType accountType) {
        return accountRepository.getActiveAccountsTotalAmountByType(userId, accountType);
    }

    @Override
    public BigDecimal getInactiveAccountsTotalAmountByType(Long userId, AccountType accountType) {
        return accountRepository.getInactiveAccountsTotalAmountByType(userId, accountType);
    }

    @Override
    public void update(Account account) {
        accountRepository.save(account);
    }

    @Override
    public Account create(Long userId, Long personId, Account account) {
        Person person = getEntityFromOptional(personRepository.get(userId, personId), personId);
        account.setPerson(person);
        return accountRepository.save(account);
    }

    @Override
    public void save(Account account) {
        accountRepository.save(account);
    }

    @Override
    public int delete(Long userId, Long accountId) {
        return accountRepository.delete(userId, accountId);
    }

    @Override
    public int deleteAllInactiveByPerson(Long userId, Long personId) {
        return accountRepository.deleteAllInactiveByPerson(userId, personId);
    }

    @Override
    public int deleteAllInactive(Long userId) {
        return accountRepository.deleteAllInactive(userId);
    }
}
