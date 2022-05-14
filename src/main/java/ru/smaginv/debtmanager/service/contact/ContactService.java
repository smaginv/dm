package ru.smaginv.debtmanager.service.contact;

import ru.smaginv.debtmanager.web.dto.contact.ContactDto;
import ru.smaginv.debtmanager.web.dto.contact.ContactIdDto;
import ru.smaginv.debtmanager.web.dto.person.PersonIdDto;

import java.util.List;

public interface ContactService {

    ContactDto get(ContactIdDto contactIdDto, PersonIdDto personIdDto);

    List<ContactDto> getAllByPerson(PersonIdDto personIdDto);

    List<ContactDto> getAll();

    ContactDto update(ContactDto contactDto, PersonIdDto personIdDto);

    ContactDto save(ContactDto contactDto, PersonIdDto personIdDto);

    void delete(ContactIdDto contactIdDto, PersonIdDto personIdDto);

    void deleteAllByPerson(PersonIdDto personIdDto);
}
