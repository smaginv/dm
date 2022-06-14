package ru.smaginv.debtmanager.repository.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.smaginv.debtmanager.entity.contact.Contact;
import ru.smaginv.debtmanager.entity.person.Person;
import ru.smaginv.debtmanager.entity.user.User;
import ru.smaginv.debtmanager.repository.user.UserRepositoryJpa;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class PersonRepositoryImpl implements PersonRepository {

    private final PersonRepositoryJpa personRepository;
    private final UserRepositoryJpa userRepository;

    @Autowired
    public PersonRepositoryImpl(PersonRepositoryJpa personRepository, UserRepositoryJpa userRepository) {
        this.personRepository = personRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Optional<Person> get(Long userId, Long personId) {
        return personRepository.get(userId, personId);
    }

    @Override
    public Optional<Person> getByContact(Long userId, Contact contact) {
        return personRepository.getByContact(userId, contact);
    }

    @Override
    public List<Person> getAll(Long userId) {
        return personRepository.getAll(userId);
    }

    @Override
    public List<Person> find(Long userId, Person person, Contact contact) {
        if (Objects.isNull(contact) || Objects.isNull(contact.getValue()))
            return personRepository.find(userId, person);
        return personRepository.find(userId, person, contact);
    }

    @Override
    public void update(Person person) {
        personRepository.save(person);
    }

    @Override
    public Person create(Long userId, Person person) {
        User user = userRepository.getReferenceById(userId);
        person.setUser(user);
        return personRepository.save(person);
    }

    @Override
    public int delete(Long userId, Long personId) {
        return personRepository.delete(userId, personId);
    }

    @Override
    public int deleteByContact(Long userId, Contact contact) {
        return personRepository.deleteByContact(userId, contact);
    }
}
