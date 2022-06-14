package ru.smaginv.debtmanager.service.contact;

import ru.smaginv.debtmanager.entity.contact.Contact;
import ru.smaginv.debtmanager.web.dto.contact.AbstractContactDto;
import ru.smaginv.debtmanager.web.dto.contact.ContactDto;
import ru.smaginv.debtmanager.web.dto.contact.ContactIdDto;
import ru.smaginv.debtmanager.web.dto.contact.ContactUpdateDto;
import ru.smaginv.debtmanager.web.dto.person.PersonIdDto;

import java.util.List;

public interface ContactService {

    ContactDto get(Long userId, ContactIdDto contactIdDto);

    List<ContactDto> getAllByPerson(Long userId, PersonIdDto personIdDto);

    List<ContactDto> getAll(Long userId);

    void update(Long userId, ContactUpdateDto contactUpdateDto);

    ContactDto create(Long userId, ContactDto contactDto);

    void delete(Long userId, ContactIdDto contactIdDto);

    void deleteAllByPerson(Long userId, PersonIdDto personIdDto);

    <T extends AbstractContactDto> Contact map(T contactDto);

    <T extends AbstractContactDto> T validate(T contactDto);
}
