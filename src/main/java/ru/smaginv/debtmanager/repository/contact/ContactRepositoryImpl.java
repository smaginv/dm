package ru.smaginv.debtmanager.repository.contact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.smaginv.debtmanager.entity.contact.Contact;
import ru.smaginv.debtmanager.entity.person.Person;
import ru.smaginv.debtmanager.repository.person.PersonRepositoryJpa;

import java.util.List;
import java.util.Optional;

@Repository
public class ContactRepositoryImpl implements ContactRepository {

    private final ContactRepositoryJpa contactRepository;
    private final PersonRepositoryJpa personRepository;

    @Autowired
    public ContactRepositoryImpl(ContactRepositoryJpa contactRepository, PersonRepositoryJpa personRepository) {
        this.contactRepository = contactRepository;
        this.personRepository = personRepository;
    }

    @Override
    public Optional<Contact> get(Long personId, Long contactId) {
        return contactRepository.get(personId, contactId);
    }

    @Override
    public List<Contact> getAllByPerson(Long personId) {
        return contactRepository.getAllByPerson(personId);
    }

    @Override
    public List<Contact> getAll() {
        return contactRepository.getAll();
    }

    @Override
    public Contact save(Long personId, Contact contact) {
        Person person = personRepository.getReferenceById(personId);
        contact.setPerson(person);
        return contactRepository.save(contact);
    }

    @Override
    public int delete(Long personId, Long contactId) {
        return contactRepository.delete(personId, contactId);
    }

    @Override
    public int deleteAllByPerson(Long personId) {
        return contactRepository.deleteAllByPerson(personId);
    }
}
