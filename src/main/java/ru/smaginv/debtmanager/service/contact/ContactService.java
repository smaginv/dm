package ru.smaginv.debtmanager.service.contact;

import ru.smaginv.debtmanager.entity.contact.Contact;
import ru.smaginv.debtmanager.web.dto.contact.AbstractContactDto;
import ru.smaginv.debtmanager.web.dto.contact.ContactDto;
import ru.smaginv.debtmanager.web.dto.contact.ContactIdDto;
import ru.smaginv.debtmanager.web.dto.contact.ContactUpdateDto;
import ru.smaginv.debtmanager.web.dto.person.PersonIdDto;

import java.util.List;

public interface ContactService {

    ContactDto get(ContactIdDto contactIdDto);

    List<ContactDto> getAllByPerson(PersonIdDto personIdDto);

    List<ContactDto> getAll();

    void update(ContactUpdateDto contactUpdateDto);

    ContactDto create(ContactDto contactDto);

    void delete(ContactIdDto contactIdDto);

    void deleteAllByPerson(PersonIdDto personIdDto);

    <T extends AbstractContactDto> Contact map(T contactDto);

    <T extends AbstractContactDto> T validate(T contactDto);
}
