package ru.smaginv.debtmanager.service.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.smaginv.debtmanager.entity.account.Account;
import ru.smaginv.debtmanager.entity.person.Person;
import ru.smaginv.debtmanager.repository.account.AccountRepository;
import ru.smaginv.debtmanager.repository.person.PersonRepository;
import ru.smaginv.debtmanager.util.MappingUtil;
import ru.smaginv.debtmanager.web.dto.person.PersonDto;
import ru.smaginv.debtmanager.web.dto.person.PersonIdDto;
import ru.smaginv.debtmanager.web.dto.person.PersonInfoDto;
import ru.smaginv.debtmanager.web.dto.person.PersonSearchDto;
import ru.smaginv.debtmanager.web.mapping.PersonMapper;

import java.util.List;

import static ru.smaginv.debtmanager.util.ValidationUtil.*;
import static ru.smaginv.debtmanager.util.entity.EntityUtil.getEntityFromOptional;

@Service
@Transactional(readOnly = true)
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final AccountRepository accountRepository;
    private final PersonMapper personMapper;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository, AccountRepository accountRepository,
                             PersonMapper personMapper) {
        this.personRepository = personRepository;
        this.accountRepository = accountRepository;
        this.personMapper = personMapper;
    }

    @Override
    public PersonInfoDto get(PersonIdDto personIdDto) {
        Person person = get(MappingUtil.map(personIdDto));
        PersonInfoDto personInfoDto = personMapper.mapInfoDto(person);
        List<Account> accounts = accountRepository.getAllByPerson(person.getId());
        personInfoDto.setAccounts(accounts);
        return personInfoDto;
    }

    @Override
    public PersonInfoDto getByPhoneNumber(String phoneNumber) {
        Person person = getEntityFromOptional(personRepository.getByPhoneNumber(phoneNumber));
        PersonInfoDto personInfoDto = personMapper.mapInfoDto(person);
        List<Account> accounts = accountRepository.getAllByPerson(person.getId());
        personInfoDto.setAccounts(accounts);
        return personInfoDto;
    }

    @Override
    public List<PersonDto> getByPhoneNumbers(String... phoneNumbers) {
        List<Person> people = personRepository.getByPhoneNumbers(phoneNumbers);
        return personMapper.mapDtos(people);
    }

    @Override
    public PersonInfoDto getByEmail(String email) {
        Person person = getEntityFromOptional(personRepository.getByEmail(email));
        PersonInfoDto personInfoDto = personMapper.mapInfoDto(person);
        List<Account> accounts = accountRepository.getAllByPerson(person.getId());
        personInfoDto.setAccounts(accounts);
        return personInfoDto;
    }

    @Override
    public List<PersonDto> getByEmails(String... emails) {
        List<Person> people = personRepository.getByEmails(emails);
        return personMapper.mapDtos(people);
    }

    @Override
    public List<PersonDto> getAll() {
        List<Person> people = personRepository.getAll();
        return personMapper.mapDtos(people);
    }

    @Override
    public List<PersonDto> find(PersonSearchDto personSearchDto) {
        Person person = personMapper.map(personSearchDto);
        List<String> contacts = List.of(personSearchDto.getPhoneNumber(), personSearchDto.getEmail());
        List<Person> people = personRepository.find(person, contacts);
        return personMapper.mapDtos(people);
    }

    @Transactional
    @Override
    public PersonDto update(PersonDto personDto) {
        Person person = get(Long.valueOf(personDto.getId()));
        personMapper.updatePerson(personDto, person);
        return personMapper.mapDto(personRepository.save(person));
    }

    @Transactional
    @Override
    public PersonDto save(PersonDto personDto) {
        checkIsNew(personDto);
        Person person = personRepository.save(personMapper.map(personDto));
        return personMapper.mapDto(person);
    }

    @Override
    public void delete(PersonIdDto personIdDto) {
        Long personId = MappingUtil.map(personIdDto);
        checkNotFoundWithId(personRepository.delete(personId) != 0, personId);
    }

    @Override
    public void deleteByPhoneNumber(String phoneNumber) {
        checkNotFound(personRepository.deleteByPhoneNumber(phoneNumber) != 0);
    }

    @Override
    public void deleteByEmail(String email) {
        checkNotFound(personRepository.deleteByEmail(email) != 0);
    }

    private Person get(Long personId) {
        return getEntityFromOptional(personRepository.get(personId), personId);
    }
}
