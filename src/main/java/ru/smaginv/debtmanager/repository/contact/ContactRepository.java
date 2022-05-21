package ru.smaginv.debtmanager.repository.contact;

import ru.smaginv.debtmanager.entity.contact.Contact;

import java.util.List;
import java.util.Optional;

public interface ContactRepository {

    Optional<Contact> get(Long personId, Long contactId);

    List<Contact> getAllByPerson(Long personId);

    List<Contact> getAll();

    Contact save(Long personId, Contact contact);

    int delete(Long personId, Long contactId);

    int deleteAllByPerson(Long personId);
}
