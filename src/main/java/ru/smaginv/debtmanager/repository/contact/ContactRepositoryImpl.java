package ru.smaginv.debtmanager.repository.contact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.smaginv.debtmanager.entity.contact.Contact;
import ru.smaginv.debtmanager.entity.person.Person;
import ru.smaginv.debtmanager.repository.person.PersonRepositoryJpa;

import java.util.List;
import java.util.Optional;

import static ru.smaginv.debtmanager.util.AppUtil.getAuthUserId;
import static ru.smaginv.debtmanager.util.entity.EntityUtil.getEntityFromOptional;

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
    public Optional<Contact> get(Long contactId) {
        return contactRepository.get(contactId, getAuthUserId());
    }

    @Override
    public List<Contact> getAllByPerson(Long personId) {
        return contactRepository.getAllByPerson(personId, getAuthUserId());
    }

    @Override
    public List<Contact> getAll() {
        return contactRepository.getAll(getAuthUserId());
    }

    @Override
    public Contact update(Contact contact) {
        return contactRepository.save(contact);
    }

    @Override
    public Contact create(Long personId, Contact contact) {
        Person person = getEntityFromOptional(personRepository.get(personId, getAuthUserId()), personId);
        contact.setPerson(person);
        return contactRepository.save(contact);
    }

    @Override
    public int delete(Long contactId) {
        return contactRepository.delete(contactId, getAuthUserId());
    }

    @Override
    public int deleteAllByPerson(Long personId) {
        return contactRepository.deleteAllByPerson(personId, getAuthUserId());
    }
}
