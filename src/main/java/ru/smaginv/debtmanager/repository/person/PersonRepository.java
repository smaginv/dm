package ru.smaginv.debtmanager.repository.person;

import ru.smaginv.debtmanager.entity.contact.Contact;
import ru.smaginv.debtmanager.entity.person.Person;

import java.util.List;
import java.util.Optional;

public interface PersonRepository {

    Optional<Person> get(Long userId, Long personId);

    Optional<Person> getByContact(Long userId, Contact contact);

    List<Person> getAll(Long userId);

    List<Person> find(Long userId, Person person, Contact contact);

    void update(Person person);

    Person create(Long userId, Person person);

    int delete(Long userId, Long personId);

    int deleteByContact(Long userId, Contact contact);
}
