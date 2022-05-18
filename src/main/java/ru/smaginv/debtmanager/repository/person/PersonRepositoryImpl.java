package ru.smaginv.debtmanager.repository.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.smaginv.debtmanager.entity.contact.Contact;
import ru.smaginv.debtmanager.entity.person.Person;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class PersonRepositoryImpl implements PersonRepository {

    private final PersonRepositoryJpa personRepository;

    @Autowired
    public PersonRepositoryImpl(PersonRepositoryJpa personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Optional<Person> get(Long personId) {
        return personRepository.get(personId);
    }

    @Override
    public Optional<Person> getByContact(Contact contact) {
        return personRepository.getByContact(contact);
    }

    @Override
    public List<Person> getAll() {
        return personRepository.getAll();
    }

    @Override
    public List<Person> find(Person person, Contact contact) {
        if (Objects.isNull(contact) || Objects.isNull(contact.getValue()))
            return personRepository.find(person);
        return personRepository.find(person, contact);
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
    public int deleteByContact(Contact contact) {
        return personRepository.deleteByContact(contact);
    }
}
