package ru.smaginv.debtmanager.dm.service.contact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.smaginv.debtmanager.dm.entity.contact.Contact;
import ru.smaginv.debtmanager.dm.entity.contact.ContactType;
import ru.smaginv.debtmanager.dm.entity.contact.UniqueContact;
import ru.smaginv.debtmanager.dm.repository.contact.ContactRepository;
import ru.smaginv.debtmanager.dm.util.MappingUtil;
import ru.smaginv.debtmanager.dm.util.validation.ValidationUtil;
import ru.smaginv.debtmanager.dm.web.dto.contact.AbstractContactDto;
import ru.smaginv.debtmanager.dm.web.dto.contact.ContactDto;
import ru.smaginv.debtmanager.dm.web.dto.contact.ContactIdDto;
import ru.smaginv.debtmanager.dm.web.dto.contact.ContactUpdateDto;
import ru.smaginv.debtmanager.dm.web.dto.person.PersonIdDto;
import ru.smaginv.debtmanager.dm.web.mapping.ContactMapper;

import javax.validation.ValidationException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static ru.smaginv.debtmanager.dm.util.entity.EntityUtil.getEntityFromOptional;

@Service
@Transactional(readOnly = true)
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;
    private final UniqueContactService uniqueContactService;
    private final ValidationUtil validationUtil;
    private final MappingUtil mappingUtil;

    @Autowired
    public ContactServiceImpl(ContactRepository contactRepository, ContactMapper contactMapper,
                              UniqueContactService uniqueContactService, ValidationUtil validationUtil,
                              MappingUtil mappingUtil) {
        this.contactRepository = contactRepository;
        this.contactMapper = contactMapper;
        this.uniqueContactService = uniqueContactService;
        this.validationUtil = validationUtil;
        this.mappingUtil = mappingUtil;
    }

    @Override
    public ContactDto get(Long userId, ContactIdDto contactIdDto) {
        Contact contact = getContact(userId, mappingUtil.mapId(contactIdDto));
        return contactMapper.mapDto(contact);
    }

    @Override
    public List<ContactDto> getAllByPerson(Long userId, PersonIdDto personIdDto) {
        List<Contact> contacts = contactRepository.getAllByPerson(userId, mappingUtil.mapId(personIdDto));
        return contactMapper.mapDtos(contacts);
    }

    @Cacheable(
            value = "contacts",
            key = "#userId + '_all'"
    )
    @Override
    public List<ContactDto> getAll(Long userId) {
        return contactMapper.mapDtos(contactRepository.getAll(userId));
    }

    @CacheEvict(
            value = "contacts",
            key = "#userId + '_all'"
    )
    @Transactional
    @Override
    public void update(Long userId, ContactUpdateDto contactUpdateDto) {
        validationUtil.validateContact(contactUpdateDto);
        Contact contact = getContact(userId, mappingUtil.mapId(contactUpdateDto));
        if (contactUpdateDto.getValue().equals(contact.getValue()))
            throw new IllegalArgumentException("contact value already exists: " + contactUpdateDto.getValue());
        contactMapper.update(contactUpdateDto, contact);
        contact = contactRepository.update(contact);
        uniqueContactService.deleteByContactId(userId, contact.getId());
        UniqueContact uniqueContact = contactMapper.mapUnique(contact);
        uniqueContactService.save(userId, uniqueContact);
    }

    @CacheEvict(
            value = "contacts",
            key = "#userId + '_all'"
    )
    @Transactional
    @Override
    public ContactDto create(Long userId, ContactDto contactDto) {
        validationUtil.validateContact(contactDto);
        Contact contact = contactMapper.map(contactDto);
        contact = contactRepository.create(userId, mappingUtil.mapId(contactDto.getPersonId()), contact);
        UniqueContact uniqueContact = contactMapper.mapUnique(contact);
        uniqueContactService.save(userId, uniqueContact);
        return contactMapper.mapDto(contact);
    }

    @CacheEvict(
            value = "contacts",
            key = "#userId + '_all'"
    )
    @Transactional
    @Override
    public void delete(Long userId, ContactIdDto contactIdDto) {
        Long contactId = mappingUtil.mapId(contactIdDto);
        int result = contactRepository.delete(userId, contactId);
        validationUtil.checkNotFoundWithId(result != 0, contactId);
        uniqueContactService.deleteByContactId(userId, contactId);
    }

    @CacheEvict(
            value = "contacts",
            key = "#userId + '_all'"
    )
    @Transactional
    @Override
    public void deleteAllByPerson(Long userId, PersonIdDto personIdDto) {
        Long personId = mappingUtil.mapId(personIdDto);
        List<Long> contactIds = contactRepository.getAllByPerson(userId, personId)
                .stream()
                .map(Contact::getId)
                .toList();
        validationUtil.checkNotFound(contactRepository.deleteAllByPerson(userId, personId) != 0);
        uniqueContactService.deleteByContactIds(userId, contactIds);
    }

    @Override
    public <T extends AbstractContactDto> Contact map(T contactDto) {
        return contactMapper.map(contactDto);
    }

    @Override
    public <T extends AbstractContactDto> T validate(T contactDto) {
        if (Objects.isNull(contactDto) ||
                (Objects.isNull(contactDto.getType()) || Objects.isNull(contactDto.getValue())))
            return null;
        List<String> contactTypes = Arrays.stream(ContactType.values())
                .map(Enum::name)
                .toList();
        if (!contactTypes.contains(contactDto.getType()))
            throw new ValidationException("type must be: " + contactTypes);
        validationUtil.validateContact(contactDto);
        return contactDto;
    }

    private Contact getContact(Long userId, Long contactId) {
        return getEntityFromOptional(contactRepository.get(userId, contactId), contactId);
    }
}
