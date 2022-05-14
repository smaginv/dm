package ru.smaginv.debtmanager.repository.contact;

import ru.smaginv.debtmanager.entity.contact.Contact;

import java.util.List;
import java.util.Optional;

public interface ContactRepository {

    Optional<Contact> get(Long contactId, Long personId);

    List<Contact> getAllByPerson(Long personId);

    List<Contact> getAll();

    Contact save(Contact contact, Long personId);

    int delete(Long contactId, Long personId);

    int deleteAllByPerson(Long personId);
}
