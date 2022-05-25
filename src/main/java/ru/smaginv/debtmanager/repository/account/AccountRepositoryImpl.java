package ru.smaginv.debtmanager.repository.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.smaginv.debtmanager.entity.account.Account;
import ru.smaginv.debtmanager.entity.account.AccountStatus;
import ru.smaginv.debtmanager.entity.account.AccountType;
import ru.smaginv.debtmanager.entity.person.Person;
import ru.smaginv.debtmanager.repository.person.PersonRepositoryJpa;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
    public Optional<Account> get(Long personId, Long accountId) {
        return accountRepository.get(personId, accountId);
    }

    @Override
    public Optional<Account> getById(Long accountId) {
        return accountRepository.get(accountId);
    }

    @Override
    public List<Account> getAll() {
        return accountRepository.getAll();
    }

    @Override
    public List<Account> getAllByPerson(Long personId) {
        return accountRepository.getAllByPerson(personId);
    }

    @Override
    public List<Account> getByState(Long personId, AccountStatus accountStatus) {
        if (Objects.isNull(personId))
            return accountRepository.getByState(accountStatus);
        return accountRepository.getByPersonAndState(personId, accountStatus);
    }

    @Override
    public List<Account> getAllByType(AccountType accountType) {
        return accountRepository.getAllByType(accountType);
    }

    @Override
    public Account save(Long personId, Account account) {
        Person person = personRepository.getReferenceById(personId);
        account.setPerson(person);
        return accountRepository.save(account);
    }

    @Override
    public void save(Account account) {
        accountRepository.save(account);
    }

    @Override
    public int delete(Long personId, Long accountId) {
        return accountRepository.delete(personId, accountId);
    }

    @Override
    public int deleteAllInactiveByPerson(Long personId) {
        return accountRepository.deleteAllInactiveByPerson(personId);
    }

    @Override
    public int deleteAllInactive() {
        return accountRepository.deleteAllInactive();
    }
}
