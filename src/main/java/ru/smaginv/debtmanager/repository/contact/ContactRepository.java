package ru.smaginv.debtmanager.repository.contact;

import ru.smaginv.debtmanager.entity.contact.Contact;

import java.util.List;
import java.util.Optional;

public interface ContactRepository {

    Optional<Contact> get(Long userId, Long contactId);

    List<Contact> getAllByPerson(Long userId, Long personId);

    List<Contact> getAll(Long userId);

    Contact update(Contact contact);

    Contact create(Long userId, Long personId, Contact contact);

    int delete(Long userId, Long contactId);

    int deleteAllByPerson(Long userId, Long personId);
}
