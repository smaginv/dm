package ru.smaginv.debtmanager.service.person;

import ru.smaginv.debtmanager.web.dto.person.PersonDto;
import ru.smaginv.debtmanager.web.dto.person.PersonIdDto;
import ru.smaginv.debtmanager.web.dto.person.PersonInfoDto;
import ru.smaginv.debtmanager.web.dto.person.PersonSearchDto;

import java.util.List;

public interface PersonService {

    PersonInfoDto get(PersonIdDto personIdDto);

    PersonInfoDto getByPhoneNumber(String phoneNumber);

    List<PersonDto> getByPhoneNumbers(String... phoneNumbers);

    PersonInfoDto getByEmail(String email);

    List<PersonDto> getByEmails(String... emails);

    List<PersonDto> getAll();

    List<PersonDto> find(PersonSearchDto personSearchDto);

    PersonDto update(PersonDto personDto);

    PersonDto save(PersonDto personDto);

    void delete(PersonIdDto personIdDto);

    void deleteByPhoneNumber(String phoneNumber);

    void deleteByEmail(String email);
}
