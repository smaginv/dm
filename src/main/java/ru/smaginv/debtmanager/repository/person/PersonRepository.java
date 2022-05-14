package ru.smaginv.debtmanager.repository.person;

import ru.smaginv.debtmanager.entity.person.Person;

import java.util.List;
import java.util.Optional;

public interface PersonRepository {

    Optional<Person> get(Long personId);

    Optional<Person> getByPhoneNumber(String phoneNumber);

    List<Person> getByPhoneNumbers(String... phoneNumbers);

    Optional<Person> getByEmail(String email);

    List<Person> getByEmails(String... emails);

    List<Person> getAll();

    List<Person> find(Person person, List<String> contacts);

    Person save(Person person);

    int delete(Long personId);

    int deleteByPhoneNumber(String phoneNumber);

    int deleteByEmail(String email);
}
