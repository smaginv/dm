package ru.smaginv.debtmanager.service.contact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.smaginv.debtmanager.entity.contact.Contact;
import ru.smaginv.debtmanager.repository.contact.ContactRepository;
import ru.smaginv.debtmanager.util.MappingUtil;
import ru.smaginv.debtmanager.util.ValidationUtil;
import ru.smaginv.debtmanager.web.dto.contact.ContactDto;
import ru.smaginv.debtmanager.web.mapping.ContactMapper;

import java.util.List;

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
    public ContactDto get(Long contactId, Long personId) {
        Contact contact = getContact(contactId, personId);
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
    public ContactDto update(ContactDto contactDto, Long personId) {
        validationUtil.checkContact(contactDto);
        Contact contact = getContact(mappingUtil.mapId(contactDto), personId);
        contactMapper.update(contactDto, contact);
        return contactMapper.mapDto(contactRepository.save(contact, personId));
    }

    @Transactional
    @Override
    public ContactDto create(ContactDto contactDto, Long personId) {
        validationUtil.checkIsNew(contactDto);
        validationUtil.checkContact(contactDto);
        Contact contact = contactRepository.save(contactMapper.map(contactDto), personId);
        return contactMapper.mapDto(contact);
    }

    @Override
    public void delete(Long contactId, Long personId) {
        int result = contactRepository.delete(contactId, personId);
        validationUtil.checkNotFoundWithId(result != 0, contactId);
    }

    @Override
    public void deleteAllByPerson(Long personId) {
        validationUtil.checkNotFound(contactRepository.deleteAllByPerson(personId) != 0);
    }

    private Contact getContact(Long contactId, Long personId) {
        return getEntityFromOptional(contactRepository.get(contactId, personId), contactId);
    }
}
