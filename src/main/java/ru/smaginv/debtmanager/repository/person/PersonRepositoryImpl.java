package ru.smaginv.debtmanager.repository.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.smaginv.debtmanager.entity.person.Person;

import java.util.List;

@Repository
public class PersonRepositoryImpl implements PersonRepository {

    private final PersonRepositoryJpa personRepository;

    @Autowired
    public PersonRepositoryImpl(PersonRepositoryJpa personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Person get(Long personId) {
        return personRepository.get(personId);
    }

    @Override
    public Person getWithAccounts(Long personId) {
        return personRepository.getWithAccounts(personId);
    }

    @Override
    public Person getByPhoneNumber(String phoneNumber) {
        return personRepository.getByPhoneNumber(phoneNumber);
    }

    @Override
    public Person getWithAccountsByPhoneNumber(String phoneNumber) {
        return personRepository.getWithAccountsByPhoneNumber(phoneNumber);
    }

    @Override
    public List<Person> getByPhoneNumbers(String... phoneNumbers) {
        return personRepository.getByPhoneNumbers(phoneNumbers);
    }

    @Override
    public Person getByEmail(String email) {
        return personRepository.getByEmail(email);
    }

    @Override
    public Person getWithAccountsByEmail(String email) {
        return personRepository.getWithAccountsByEmail(email);
    }

    @Override
    public List<Person> getByEmails(String... emails) {
        return personRepository.getByEmails(emails);
    }

    @Override
    public List<Person> getAll() {
        return personRepository.getAll();
    }

    @Override
    public Person save(Person person) {
        return personRepository.save(person);
    }

    @Override
    public int delete(Long personId) {
        return personRepository.delete(personId);
    }

    @Override
    public int deleteByPhoneNumber(String phoneNumber) {
        return personRepository.deleteByPhoneNumber(phoneNumber);
    }

    @Override
    public int deleteByEmail(String email) {
        return personRepository.deleteByEmail(email);
    }
}
