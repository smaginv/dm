package ru.smaginv.debtmanager.service.contact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.smaginv.debtmanager.entity.contact.Contact;
import ru.smaginv.debtmanager.entity.contact.ContactType;
import ru.smaginv.debtmanager.entity.contact.UniqueContact;
import ru.smaginv.debtmanager.repository.contact.ContactRepository;
import ru.smaginv.debtmanager.util.MappingUtil;
import ru.smaginv.debtmanager.util.validation.ValidationUtil;
import ru.smaginv.debtmanager.web.dto.contact.AbstractContactDto;
import ru.smaginv.debtmanager.web.dto.contact.ContactDto;
import ru.smaginv.debtmanager.web.dto.contact.ContactIdDto;
import ru.smaginv.debtmanager.web.dto.contact.ContactUpdateDto;
import ru.smaginv.debtmanager.web.dto.person.PersonIdDto;
import ru.smaginv.debtmanager.web.mapping.ContactMapper;

import javax.validation.ValidationException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static ru.smaginv.debtmanager.util.entity.EntityUtil.getEntityFromOptional;

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
    public ContactDto get(ContactIdDto contactIdDto) {
        Contact contact = getContact(mappingUtil.mapId(contactIdDto));
        return contactMapper.mapDto(contact);
    }

    @Override
    public List<ContactDto> getAllByPerson(PersonIdDto personIdDto) {
        List<Contact> contacts = contactRepository.getAllByPerson(mappingUtil.mapId(personIdDto));
        return contactMapper.mapDtos(contacts);
    }

    @Override
    public List<ContactDto> getAll() {
        return contactMapper.mapDtos(contactRepository.getAll());
    }

    @Transactional
    @Override
    public void update(ContactUpdateDto contactUpdateDto) {
        validationUtil.validateContact(contactUpdateDto);
        Contact contact = getContact(mappingUtil.mapId(contactUpdateDto));
        if (contactUpdateDto.getValue().equals(contact.getValue()))
            throw new IllegalArgumentException("contact value already exists: " + contactUpdateDto.getValue());
        contactMapper.update(contactUpdateDto, contact);
        contact = contactRepository.update(contact);
        uniqueContactService.deleteByContactId(contact.getId());
        UniqueContact uniqueContact = contactMapper.mapUnique(contact);
        uniqueContactService.save(uniqueContact);
    }

    @Transactional
    @Override
    public ContactDto create(ContactDto contactDto) {
        validationUtil.validateContact(contactDto);
        Contact contact = contactMapper.map(contactDto);
        contact = contactRepository.create(mappingUtil.mapId(contactDto.getPersonId()), contact);
        UniqueContact uniqueContact = contactMapper.mapUnique(contact);
        uniqueContactService.save(uniqueContact);
        return contactMapper.mapDto(contact);
    }

    @Transactional
    @Override
    public void delete(ContactIdDto contactIdDto) {
        Long contactId = mappingUtil.mapId(contactIdDto);
        int result = contactRepository.delete(contactId);
        validationUtil.checkNotFoundWithId(result != 0, contactId);
        uniqueContactService.deleteByContactId(contactId);
    }

    @Transactional
    @Override
    public void deleteAllByPerson(PersonIdDto personIdDto) {
        Long personId = mappingUtil.mapId(personIdDto);
        List<Long> contactIds = contactRepository.getAllByPerson(personId)
                .stream()
                .map(Contact::getId)
                .toList();
        validationUtil.checkNotFound(contactRepository.deleteAllByPerson(personId) != 0);
        uniqueContactService.deleteByContactIds(contactIds);
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

    private Contact getContact(Long contactId) {
        return getEntityFromOptional(contactRepository.get(contactId), contactId);
    }
}
