package ru.smaginv.debtmanager.service.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.smaginv.debtmanager.entity.person.Person;
import ru.smaginv.debtmanager.repository.person.PersonRepository;
import ru.smaginv.debtmanager.service.account.AccountService;
import ru.smaginv.debtmanager.service.contact.ContactService;
import ru.smaginv.debtmanager.util.MappingUtil;
import ru.smaginv.debtmanager.util.ValidationUtil;
import ru.smaginv.debtmanager.web.dto.account.AccountDto;
import ru.smaginv.debtmanager.web.dto.contact.ContactDto;
import ru.smaginv.debtmanager.web.dto.person.PersonDto;
import ru.smaginv.debtmanager.web.dto.person.PersonInfoDto;
import ru.smaginv.debtmanager.web.dto.person.PersonSearchDto;
import ru.smaginv.debtmanager.web.mapping.PersonMapper;

import java.util.List;

import static ru.smaginv.debtmanager.util.entity.EntityUtil.getEntityFromOptional;

@Service
@Transactional(readOnly = true)
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final AccountService accountService;
    private final ContactService contactService;
    private final PersonMapper personMapper;
    private final ValidationUtil validationUtil;
    private final MappingUtil mappingUtil;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository, AccountService accountService,
                             ContactService contactService, PersonMapper personMapper,
                             ValidationUtil validationUtil, MappingUtil mappingUtil) {
        this.personRepository = personRepository;
        this.accountService = accountService;
        this.contactService = contactService;
        this.personMapper = personMapper;
        this.validationUtil = validationUtil;
        this.mappingUtil = mappingUtil;
    }

    @Override
    public PersonInfoDto get(Long personId) {
        Person person = getPerson(personId);
        PersonInfoDto personInfoDto = personMapper.mapInfoDto(person);
        List<AccountDto> accounts = accountService.getAllByPerson(personId);
        List<ContactDto> contacts = contactService.getAllByPerson(personId);
        personInfoDto.setAccounts(accounts);
        personInfoDto.setContacts(contacts);
        return personInfoDto;
    }

    @Override
    public PersonInfoDto getByPhoneNumber(String phoneNumber) {
        Person person = getEntityFromOptional(personRepository.getByPhoneNumber(phoneNumber));
        PersonInfoDto personInfoDto = personMapper.mapInfoDto(person);
        List<AccountDto> accounts = accountService.getAllByPerson(person.getId());
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
        List<AccountDto> accounts = accountService.getAllByPerson(person.getId());
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
        Person person = getPerson(mappingUtil.mapId(personDto));
        personMapper.update(personDto, person);
        return personMapper.mapDto(personRepository.save(person));
    }

    @Transactional
    @Override
    public PersonDto create(PersonDto personDto) {
        validationUtil.checkIsNew(personDto);
        Person person = personRepository.save(personMapper.map(personDto));
        return personMapper.mapDto(person);
    }

    @Override
    public void delete(Long personId) {
        validationUtil.checkNotFoundWithId(personRepository.delete(personId) != 0, personId);
    }

    @Override
    public void deleteByPhoneNumber(String phoneNumber) {
        validationUtil.checkNotFound(personRepository.deleteByPhoneNumber(phoneNumber) != 0);
    }

    @Override
    public void deleteByEmail(String email) {
        validationUtil.checkNotFound(personRepository.deleteByEmail(email) != 0);
    }

    private Person getPerson(Long personId) {
        return getEntityFromOptional(personRepository.get(personId), personId);
    }
}
