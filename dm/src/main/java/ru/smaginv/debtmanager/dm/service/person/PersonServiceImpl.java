package ru.smaginv.debtmanager.dm.service.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.smaginv.debtmanager.dm.entity.contact.Contact;
import ru.smaginv.debtmanager.dm.entity.person.Person;
import ru.smaginv.debtmanager.dm.repository.person.PersonRepository;
import ru.smaginv.debtmanager.dm.service.account.AccountService;
import ru.smaginv.debtmanager.dm.service.contact.ContactService;
import ru.smaginv.debtmanager.dm.util.MappingUtil;
import ru.smaginv.debtmanager.dm.util.validation.ValidationUtil;
import ru.smaginv.debtmanager.dm.web.dto.account.AccountDto;
import ru.smaginv.debtmanager.dm.web.dto.contact.ContactDto;
import ru.smaginv.debtmanager.dm.web.dto.contact.ContactSearchDto;
import ru.smaginv.debtmanager.dm.web.dto.person.*;
import ru.smaginv.debtmanager.dm.web.mapping.PersonMapper;

import java.util.List;

import static ru.smaginv.debtmanager.dm.util.entity.EntityUtil.getEntityFromOptional;

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
    public PersonInfoDto get(Long userId, PersonIdDto personIdDto) {
        Long personId = mappingUtil.mapId(personIdDto);
        PersonInfoDto personInfoDto = personMapper.mapInfoDto(getPerson(userId, personId));
        List<AccountDto> accounts = accountService.getAllByPerson(userId, personMapper.mapIdDto(personId));
        List<ContactDto> contacts = contactService.getAllByPerson(userId, personMapper.mapIdDto(personId));
        personInfoDto.setAccounts(accounts);
        personInfoDto.setContacts(contacts);
        return personInfoDto;
    }

    @Override
    public PersonInfoDto getByContact(Long userId, ContactSearchDto contactSearchDto) {
        validationUtil.validateContact(contactSearchDto);
        Contact contact = contactService.map(contactSearchDto);
        Person person = getEntityFromOptional(personRepository.getByContact(userId, contact));
        PersonInfoDto personInfoDto = personMapper.mapInfoDto(person);
        List<AccountDto> accounts = accountService.getAllByPerson(userId, personMapper.mapIdDto(person.getId()));
        List<ContactDto> contacts = contactService.getAllByPerson(userId, personMapper.mapIdDto(person.getId()));
        personInfoDto.setAccounts(accounts);
        personInfoDto.setContacts(contacts);
        return personInfoDto;
    }

    @Cacheable(
            value = "people",
            key = "#userId + '_all'"
    )
    @Override
    public List<PersonDto> getAll(Long userId) {
        List<Person> people = personRepository.getAll(userId);
        return personMapper.mapDtos(people);
    }

    @Override
    public List<PersonDto> find(Long userId, PersonSearchDto personSearchDto) {
        Person person = personMapper.map(personSearchDto);
        ContactSearchDto contactSearchDto = contactService.validate(personSearchDto.getContact());
        Contact contact = contactService.map(contactSearchDto);
        List<Person> people = personRepository.find(userId, person, contact);
        return personMapper.mapDtos(people);
    }

    @CacheEvict(
            value = "people",
            key = "#userId + '_all'"
    )
    @Transactional
    @Override
    public void update(Long userId, PersonUpdateDto personUpdateDto) {
        Person person = getPerson(userId, mappingUtil.mapId(personUpdateDto));
        personMapper.update(personUpdateDto, person);
        personRepository.update(person);
    }

    @CacheEvict(
            value = "people",
            key = "#userId + '_all'"
    )
    @Transactional
    @Override
    public PersonDto create(Long userId, PersonDto personDto) {
        Person person = personRepository.create(userId, personMapper.map(personDto));
        return personMapper.mapDto(person);
    }

    @CacheEvict(
            value = "people",
            key = "#userId + '_all'"
    )
    @Transactional
    @Override
    public void delete(Long userId, PersonIdDto personIdDto) {
        Long personId = mappingUtil.mapId(personIdDto);
        validationUtil.checkNotFoundWithId(personRepository.delete(userId, personId) != 0, personId);
    }

    @CacheEvict(
            value = "people",
            key = "#userId + '_all'"
    )
    @Transactional
    @Override
    public void deleteByContact(Long userId, ContactSearchDto contactSearchDto) {
        validationUtil.validateContact(contactSearchDto);
        Contact contact = contactService.map(contactSearchDto);
        validationUtil.checkNotFound(personRepository.deleteByContact(userId, contact) != 0);
    }

    private Person getPerson(Long userId, Long personId) {
        return getEntityFromOptional(personRepository.get(userId, personId), personId);
    }
}
