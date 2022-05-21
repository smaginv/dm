package ru.smaginv.debtmanager.service.contact;

import ru.smaginv.debtmanager.entity.contact.Contact;
import ru.smaginv.debtmanager.web.dto.contact.AbstractContactDto;
import ru.smaginv.debtmanager.web.dto.contact.ContactDto;
import ru.smaginv.debtmanager.web.dto.contact.ContactSearchDto;

import java.util.List;

public interface ContactService {

    ContactDto get(Long personId, Long contactId);

    List<ContactDto> getAllByPerson(Long personId);

    List<ContactDto> getAll();

    void update(Long personId, ContactDto contactDto);

    ContactDto create(Long personId, ContactDto contactDto);

    void delete(Long personId, Long contactId);

    void deleteAllByPerson(Long personId);

    Contact map(ContactSearchDto contactSearchDto);

    <T extends AbstractContactDto> T validate(T contactDto);
}
