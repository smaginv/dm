package ru.smaginv.debtmanager.service.person;

import ru.smaginv.debtmanager.web.dto.contact.ContactSearchDto;
import ru.smaginv.debtmanager.web.dto.person.*;

import java.util.List;

public interface PersonService {

    PersonInfoDto get(PersonIdDto personIdDto);

    PersonInfoDto getByContact(ContactSearchDto contactSearchDto);

    List<PersonDto> getAll();

    List<PersonDto> find(PersonSearchDto personSearchDto);

    void update(PersonUpdateDto personUpdateDto);

    PersonDto create(PersonDto personDto);

    void delete(PersonIdDto personIdDto);

    void deleteByContact(ContactSearchDto contactSearchDto);
}
