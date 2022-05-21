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
    public Optional<Contact> get(Long contactId, Long personId) {
        return contactRepository.get(contactId, personId);
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
    public Contact save(Contact contact, Long personId) {
        if (!contact.isNew() && get(contact.getId(), personId).isEmpty())
            return null;
        Person person = personRepository.getById(personId);
        contact.setPerson(person);
        return contactRepository.save(contact);
    }

    @Override
    public int delete(Long contactId, Long personId) {
        return contactRepository.delete(contactId, personId);
    }

    @Override
    public int deleteAllByPerson(Long personId) {
        return contactRepository.deleteAllByPerson(personId);
    }
}