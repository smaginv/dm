package ru.smaginv.debtmanager.repository.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.smaginv.debtmanager.entity.account.Account;
import ru.smaginv.debtmanager.entity.person.Person;
import ru.smaginv.debtmanager.repository.person.PersonRepositoryJpa;

import java.util.List;
import java.util.Objects;

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
    public Account get(Long accountId, Long personId) {
        return accountRepository.get(accountId, personId);
    }

    @Override
    public Account getWithOperations(Long accountId, Long personId) {
        return accountRepository.getWithOperations(accountId, personId);
    }

    @Override
    public List<Account> getAll() {
        return accountRepository.getAll();
    }

    @Override
    public List<Account> getAllWithOperations() {
        return accountRepository.getAllWithOperations();
    }

    @Override
    public List<Account> getAllByPerson(Long personId) {
        return accountRepository.getAllByPerson(personId);
    }

    @Override
    public List<Account> getAllActive() {
        return accountRepository.getAllActive();
    }

    @Override
    public List<Account> getAllActiveByPerson(Long personId) {
        return accountRepository.getAllActiveByPerson(personId);
    }

    @Override
    public List<Account> getAllInactive() {
        return accountRepository.getAllInactive();
    }

    @Override
    public List<Account> getAllInactiveByPerson(Long personId) {
        return accountRepository.getAllInactiveByPerson(personId);
    }

    @Override
    public List<Account> getAllDebit() {
        return accountRepository.getAllDebit();
    }

    @Override
    public List<Account> getAllCredit() {
        return accountRepository.getAllCredit();
    }

    @Override
    public Account save(Account account, Long personId) {
        Long accountId = account.getId();
        if (Objects.nonNull(accountId) && Objects.isNull(get(accountId, personId)))
            return null;
        Person person = personRepository.getById(personId);
        account.setPerson(person);
        return accountRepository.save(account);
    }

    @Override
    public int delete(Long accountId, Long personId) {
        return accountRepository.delete(accountId, personId);
    }

    @Override
    public int deleteAllByPerson(Long personId) {
        return accountRepository.deleteAllByPerson(personId);
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
