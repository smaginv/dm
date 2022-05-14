package ru.smaginv.debtmanager.service.contact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.smaginv.debtmanager.entity.contact.Contact;
import ru.smaginv.debtmanager.repository.contact.ContactRepository;
import ru.smaginv.debtmanager.util.MappingUtil;
import ru.smaginv.debtmanager.web.dto.contact.ContactDto;
import ru.smaginv.debtmanager.web.dto.contact.ContactIdDto;
import ru.smaginv.debtmanager.web.dto.person.PersonIdDto;
import ru.smaginv.debtmanager.web.mapping.ContactMapper;

import java.util.List;

import static ru.smaginv.debtmanager.util.ValidationUtil.*;
import static ru.smaginv.debtmanager.util.entity.EntityUtil.getEntityFromOptional;

@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;

    @Autowired
    public ContactServiceImpl(ContactRepository contactRepository, ContactMapper contactMapper) {
        this.contactRepository = contactRepository;
        this.contactMapper = contactMapper;
    }

    @Override
    public ContactDto get(ContactIdDto contactIdDto, PersonIdDto personIdDto) {
        Contact contact = get(MappingUtil.map(contactIdDto), MappingUtil.map(personIdDto));
        return contactMapper.mapDto(contact);
    }

    @Override
    public List<ContactDto> getAllByPerson(PersonIdDto personIdDto) {
        List<Contact> contacts = contactRepository.getAllByPerson(MappingUtil.map(personIdDto));
        return contactMapper.mapDtos(contacts);
    }

    @Override
    public List<ContactDto> getAll() {
        return contactMapper.mapDtos(contactRepository.getAll());
    }

    @Override
    public ContactDto update(ContactDto contactDto, PersonIdDto personIdDto) {
        Contact contact = get(MappingUtil.map(contactDto), MappingUtil.map(personIdDto));
        contactMapper.updateContact(contactDto, contact);
        return contactMapper.mapDto(contact);
    }

    @Override
    public ContactDto save(ContactDto contactDto, PersonIdDto personIdDto) {
        checkIsNew(contactDto);
        Contact contact = contactRepository.save(contactMapper.map(contactDto), MappingUtil.map(personIdDto));
        return contactMapper.mapDto(contact);
    }

    @Override
    public void delete(ContactIdDto contactIdDto, PersonIdDto personIdDto) {
        int result = contactRepository.delete(MappingUtil.map(contactIdDto), MappingUtil.map(personIdDto));
        checkNotFoundWithId(result != 0, contactIdDto);
    }

    @Override
    public void deleteAllByPerson(PersonIdDto personIdDto) {
        checkNotFound(contactRepository.deleteAllByPerson(MappingUtil.map(personIdDto)) != 0);
    }

    private Contact get(Long contactId, Long personId) {
        return getEntityFromOptional(contactRepository.get(contactId, personId), contactId);
    }
}
