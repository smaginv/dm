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

import static ru.smaginv.debtmanager.util.AppUtil.getAuthUserId;

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
    public Optional<Person> get(Long personId) {
        return personRepository.get(personId, getAuthUserId());
    }

    @Override
    public Optional<Person> getByContact(Contact contact) {
        return personRepository.getByContact(contact, getAuthUserId());
    }

    @Override
    public List<Person> getAll() {
        return personRepository.getAll(getAuthUserId());
    }

    @Override
    public List<Person> find(Person person, Contact contact) {
        if (Objects.isNull(contact) || Objects.isNull(contact.getValue()))
            return personRepository.find(person, getAuthUserId());
        return personRepository.find(person, contact, getAuthUserId());
    }

    @Override
    public void update(Person person) {
        personRepository.save(person);
    }

    @Override
    public Person create(Person person) {
        User user = userRepository.getReferenceById(Objects.requireNonNull(getAuthUserId()));
        person.setUser(user);
        return personRepository.save(person);
    }

    @Override
    public int delete(Long personId) {
        return personRepository.delete(personId, getAuthUserId());
    }

    @Override
    public int deleteByContact(Contact contact) {
        return personRepository.deleteByContact(contact, getAuthUserId());
    }
}
