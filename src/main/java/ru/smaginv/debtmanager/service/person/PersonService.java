package ru.smaginv.debtmanager.service.person;

import ru.smaginv.debtmanager.web.dto.contact.ContactDto;
import ru.smaginv.debtmanager.web.dto.person.PersonDto;
import ru.smaginv.debtmanager.web.dto.person.PersonInfoDto;
import ru.smaginv.debtmanager.web.dto.person.PersonSearchDto;

import java.util.List;

public interface PersonService {

    PersonInfoDto get(Long personId);

    PersonInfoDto getByContact(ContactDto contactDto);

    List<PersonDto> getAll();

    List<PersonDto> find(PersonSearchDto personSearchDto);

    void update(PersonDto personDto);

    PersonDto create(PersonDto personDto);

    void delete(Long personId);

    void deleteByContact(ContactDto contactDto);
}
