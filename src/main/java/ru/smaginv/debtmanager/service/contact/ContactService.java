package ru.smaginv.debtmanager.service.contact;

import ru.smaginv.debtmanager.web.dto.contact.ContactDto;

import java.util.List;

public interface ContactService {

    ContactDto get(Long contactId, Long personId);

    List<ContactDto> getAllByPerson(Long personId);

    List<ContactDto> getAll();

    ContactDto update(ContactDto contactDto, Long personId);

    ContactDto create(ContactDto contactDto, Long personId);

    void delete(Long contactId, Long personId);

    void deleteAllByPerson(Long personId);
}
