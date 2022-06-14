package ru.smaginv.debtmanager.service.person;

import ru.smaginv.debtmanager.web.dto.contact.ContactSearchDto;
import ru.smaginv.debtmanager.web.dto.person.*;

import java.util.List;

public interface PersonService {

    PersonInfoDto get(Long userId, PersonIdDto personIdDto);

    PersonInfoDto getByContact(Long userId, ContactSearchDto contactSearchDto);

    List<PersonDto> getAll(Long userId);

    List<PersonDto> find(Long userId, PersonSearchDto personSearchDto);

    void update(Long userId, PersonUpdateDto personUpdateDto);

    PersonDto create(Long userId, PersonDto personDto);

    void delete(Long userId, PersonIdDto personIdDto);

    void deleteByContact(Long userId, ContactSearchDto contactSearchDto);
}
