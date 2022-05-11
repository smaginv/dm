package ru.smaginv.debtmanager.repository.person;

import ru.smaginv.debtmanager.entity.person.Person;

import java.util.List;

public interface PersonRepository {

    Person get(Long personId);

    Person getWithAccounts(Long personId);

    Person getByPhoneNumber(String phoneNumber);

    Person getWithAccountsByPhoneNumber(String phoneNumber);

    List<Person> getByPhoneNumbers(String... phoneNumbers);

    Person getByEmail(String email);

    Person getWithAccountsByEmail(String email);

    List<Person> getByEmails(String... emails);

    List<Person> getAll();

    Person save(Person person);

    int delete(Long personId);

    int deleteByPhoneNumber(String phoneNumber);

    int deleteByEmail(String email);
}
