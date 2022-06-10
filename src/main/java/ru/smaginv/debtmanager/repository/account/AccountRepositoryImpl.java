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

import static ru.smaginv.debtmanager.util.AppUtil.getAuthUserId;
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
    public Optional<Account> get(Long accountId) {
        return accountRepository.get(accountId, getAuthUserId());
    }

    @Override
    public List<Account> getAll() {
        return accountRepository.getAll(getAuthUserId());
    }

    @Override
    public List<Account> getAllByPerson(Long personId) {
        return accountRepository.getAllByPerson(personId, getAuthUserId());
    }

    @Override
    public List<Account> getAllByStatus(AccountStatus accountStatus) {
        return accountRepository.getAllByStatus(accountStatus, getAuthUserId());
    }

    @Override
    public List<Account> getAllByType(AccountType accountType) {
        return accountRepository.getAllByType(accountType, getAuthUserId());
    }

    @Override
    public BigDecimal getActiveAccountsTotalAmountByType(AccountType accountType) {
        return accountRepository.getActiveAccountsTotalAmountByType(accountType, getAuthUserId());
    }

    @Override
    public BigDecimal getInactiveAccountsTotalAmountByType(AccountType accountType) {
        return accountRepository.getInactiveAccountsTotalAmountByType(accountType, getAuthUserId());
    }

    @Override
    public void update(Account account) {
        accountRepository.save(account);
    }

    @Override
    public Account create(Long personId, Account account) {
        Person person = getEntityFromOptional(personRepository.get(personId, getAuthUserId()), personId);
        account.setPerson(person);
        return accountRepository.save(account);
    }

    @Override
    public void save(Account account) {
        accountRepository.save(account);
    }

    @Override
    public int delete(Long accountId) {
        return accountRepository.delete(accountId, getAuthUserId());
    }

    @Override
    public int deleteAllInactiveByPerson(Long personId) {
        return accountRepository.deleteAllInactiveByPerson(personId, getAuthUserId());
    }

    @Override
    public int deleteAllInactive() {
        return accountRepository.deleteAllInactive(getAuthUserId());
    }
}
