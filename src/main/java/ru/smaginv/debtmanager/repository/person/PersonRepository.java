package ru.smaginv.debtmanager.repository.person;

import ru.smaginv.debtmanager.entity.contact.Contact;
import ru.smaginv.debtmanager.entity.person.Person;

import java.util.List;
import java.util.Optional;

public interface PersonRepository {

    Optional<Person> get(Long personId);

    Optional<Person> getByContact(Contact contact);

    List<Person> getAll();

    List<Person> find(Person person, Contact contact);

    Person save(Person person);

    int delete(Long personId);

    int deleteByContact(Contact contact);
}
