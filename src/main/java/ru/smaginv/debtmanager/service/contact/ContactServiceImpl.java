package ru.smaginv.debtmanager.service.contact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.smaginv.debtmanager.entity.contact.Contact;
import ru.smaginv.debtmanager.entity.contact.ContactType;
import ru.smaginv.debtmanager.repository.contact.ContactRepository;
import ru.smaginv.debtmanager.util.MappingUtil;
import ru.smaginv.debtmanager.util.validation.ValidationUtil;
import ru.smaginv.debtmanager.web.dto.contact.AbstractContactDto;
import ru.smaginv.debtmanager.web.dto.contact.ContactDto;
import ru.smaginv.debtmanager.web.dto.contact.ContactSearchDto;
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
    private final ValidationUtil validationUtil;
    private final MappingUtil mappingUtil;

    @Autowired
    public ContactServiceImpl(ContactRepository contactRepository, ContactMapper contactMapper,
                              ValidationUtil validationUtil, MappingUtil mappingUtil) {
        this.contactRepository = contactRepository;
        this.contactMapper = contactMapper;
        this.validationUtil = validationUtil;
        this.mappingUtil = mappingUtil;
    }

    @Override
    public ContactDto get(Long personId, Long contactId) {
        Contact contact = getContact(personId, contactId);
        return contactMapper.mapDto(contact);
    }

    @Override
    public List<ContactDto> getAllByPerson(Long personId) {
        List<Contact> contacts = contactRepository.getAllByPerson(personId);
        return contactMapper.mapDtos(contacts);
    }

    @Override
    public List<ContactDto> getAll() {
        return contactMapper.mapDtos(contactRepository.getAll());
    }

    @Transactional
    @Override
    public void update(Long personId, ContactDto contactDto) {
        validationUtil.validateContact(contactDto);
        Contact contact = getContact(personId, mappingUtil.mapId(contactDto));
        contactMapper.update(contactDto, contact);
        contactRepository.save(personId, contact);
    }

    @Transactional
    @Override
    public ContactDto create(Long personId, ContactDto contactDto) {
        validationUtil.checkIsNew(contactDto);
        validationUtil.validateContact(contactDto);
        Contact contact = contactRepository.save(personId, contactMapper.map(contactDto));
        return contactMapper.mapDto(contact);
    }

    @Transactional
    @Override
    public void delete(Long personId, Long contactId) {
        int result = contactRepository.delete(personId, contactId);
        validationUtil.checkNotFoundWithId(result != 0, contactId);
    }

    @Transactional
    @Override
    public void deleteAllByPerson(Long personId) {
        validationUtil.checkNotFound(contactRepository.deleteAllByPerson(personId) != 0);
    }

    @Override
    public Contact map(ContactSearchDto contactSearchDto) {
        return contactMapper.map(contactSearchDto);
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

    private Contact getContact(Long personId, Long contactId) {
        return getEntityFromOptional(contactRepository.get(personId, contactId), contactId);
    }
}
