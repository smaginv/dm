package ru.smaginv.debtmanager.repository.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import ru.smaginv.debtmanager.entity.person.Person;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static ru.smaginv.debtmanager.util.AppUtil.emptyString;

@Repository
public class PersonRepositoryImpl implements PersonRepository {

    private final PersonRepositoryJpa personRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public PersonRepositoryImpl(PersonRepositoryJpa personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Optional<Person> get(Long personId) {
        return personRepository.get(personId);
    }

    @Override
    public Optional<Person> getByPhoneNumber(String phoneNumber) {
        return personRepository.getByPhoneNumber(phoneNumber);
    }

    @Override
    public List<Person> getByPhoneNumbers(String... phoneNumbers) {
        return personRepository.getByPhoneNumbers(phoneNumbers);
    }

    @Override
    public Optional<Person> getByEmail(String email) {
        return personRepository.getByEmail(email);
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

    @Override
    public List<Person> find(Person person, List<String> contacts) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM person WHERE ");
        int queryLength = query.length();

        query.append(buildCondition("person_id", person.getId()));
        query.append(buildCondition("first_name", person.getFirstName()));
        query.append(buildCondition("last_name", person.getLastName()));
        contacts.forEach(contact -> query.append(buildSubQuery(contact)));

        if (queryLength == query.length()) {
            return Collections.emptyList();
        }

        query.delete(query.lastIndexOf(" OR "), query.length());
        TypedQuery<Person> typedQuery = entityManager.createNamedQuery(query.toString().trim(), Person.class);
        List<Person> people = typedQuery.getResultList();
        return people;
    }

    private <T> String buildCondition(String column, T value) {
        if (StringUtils.hasText(value.toString()))
            return emptyString();
        return column + " = " + "'" + value + "'" + " OR ";
    }

    private String buildSubQuery(String contact) {
        if (StringUtils.hasText(contact))
            return emptyString();
        return "(person_id IN (SELECT person_id FROM contact WHERE contact.value = '" + contact + "' OR ";
    }
}
