package ru.smaginv.debtmanager.service.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.smaginv.debtmanager.entity.contact.Contact;
import ru.smaginv.debtmanager.entity.person.Person;
import ru.smaginv.debtmanager.repository.person.PersonRepository;
import ru.smaginv.debtmanager.service.account.AccountService;
import ru.smaginv.debtmanager.service.contact.ContactService;
import ru.smaginv.debtmanager.util.MappingUtil;
import ru.smaginv.debtmanager.util.validation.ValidationUtil;
import ru.smaginv.debtmanager.web.dto.account.AccountDto;
import ru.smaginv.debtmanager.web.dto.contact.ContactDto;
import ru.smaginv.debtmanager.web.dto.contact.ContactSearchDto;
import ru.smaginv.debtmanager.web.dto.person.*;
import ru.smaginv.debtmanager.web.mapping.PersonMapper;

import java.util.List;

import static ru.smaginv.debtmanager.util.entity.EntityUtil.getEntityFromOptional;

@Service
@Transactional(readOnly = true)
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;
    private final AccountService accountService;
    private final ContactService contactService;
    private final ValidationUtil validationUtil;
    private final MappingUtil mappingUtil;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository, PersonMapper personMapper,
                             AccountService accountService, ContactService contactService,
                             ValidationUtil validationUtil, MappingUtil mappingUtil) {
        this.personRepository = personRepository;
        this.personMapper = personMapper;
        this.accountService = accountService;
        this.contactService = contactService;
        this.validationUtil = validationUtil;
        this.mappingUtil = mappingUtil;
    }

    @Override
    public PersonInfoDto get(PersonIdDto personIdDto) {
        Long personId = mappingUtil.mapId(personIdDto);
        PersonInfoDto personInfoDto = personMapper.mapInfoDto(getPerson(personId));
        List<AccountDto> accounts = accountService.getAllByPerson(personMapper.mapIdDto(personId));
        List<ContactDto> contacts = contactService.getAllByPerson(personMapper.mapIdDto(personId));
        personInfoDto.setAccounts(accounts);
        personInfoDto.setContacts(contacts);
        return personInfoDto;
    }

    @Override
    public PersonInfoDto getByContact(ContactSearchDto contactSearchDto) {
        validationUtil.validateContact(contactSearchDto);
        Contact contact = contactService.map(contactSearchDto);
        Person person = getEntityFromOptional(personRepository.getByContact(contact));
        PersonInfoDto personInfoDto = personMapper.mapInfoDto(person);
        List<AccountDto> accounts = accountService.getAllByPerson(personMapper.mapIdDto(person.getId()));
        List<ContactDto> contacts = contactService.getAllByPerson(personMapper.mapIdDto(person.getId()));
        personInfoDto.setAccounts(accounts);
        personInfoDto.setContacts(contacts);
        return personInfoDto;
    }

    @Override
    public List<PersonDto> getAll() {
        List<Person> people = personRepository.getAll();
        return personMapper.mapDtos(people);
    }

    @Override
    public List<PersonDto> find(PersonSearchDto personSearchDto) {
        Person person = personMapper.map(personSearchDto);
        ContactSearchDto contactSearchDto = contactService.validate(personSearchDto.getContact());
        Contact contact = contactService.map(contactSearchDto);
        List<Person> people = personRepository.find(person, contact);
        return personMapper.mapDtos(people);
    }

    @Transactional
    @Override
    public void update(PersonUpdateDto personUpdateDto) {
        Person person = getPerson(mappingUtil.mapId(personUpdateDto));
        personMapper.update(personUpdateDto, person);
        personRepository.update(person);
    }

    @Transactional
    @Override
    public PersonDto create(PersonDto personDto) {
        Person person = personRepository.create(personMapper.map(personDto));
        return personMapper.mapDto(person);
    }

    @Transactional
    @Override
    public void delete(PersonIdDto personIdDto) {
        Long personId = mappingUtil.mapId(personIdDto);
        validationUtil.checkNotFoundWithId(personRepository.delete(personId) != 0, personId);
    }

    @Transactional
    @Override
    public void deleteByContact(ContactSearchDto contactSearchDto) {
        validationUtil.validateContact(contactSearchDto);
        Contact contact = contactService.map(contactSearchDto);
        validationUtil.checkNotFound(personRepository.deleteByContact(contact) != 0);
    }

    private Person getPerson(Long personId) {
        return getEntityFromOptional(personRepository.get(personId), personId);
    }
}
