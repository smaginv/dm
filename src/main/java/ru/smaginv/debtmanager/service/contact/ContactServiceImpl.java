package ru.smaginv.debtmanager.service.contact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.smaginv.debtmanager.entity.contact.Contact;
import ru.smaginv.debtmanager.repository.contact.ContactRepository;
import ru.smaginv.debtmanager.web.dto.contact.ContactDto;
import ru.smaginv.debtmanager.web.dto.contact.ContactIdDto;
import ru.smaginv.debtmanager.web.dto.person.PersonIdDto;
import ru.smaginv.debtmanager.web.mapping.ContactMapper;

import java.util.List;

import static ru.smaginv.debtmanager.util.MappingUtil.mapId;
import static ru.smaginv.debtmanager.util.ValidationUtil.*;
import static ru.smaginv.debtmanager.util.entity.EntityUtil.getEntityFromOptional;

@Service
@Transactional(readOnly = true)
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
        Contact contact = get(mapId(contactIdDto), mapId(personIdDto));
        return contactMapper.mapDto(contact);
    }

    @Override
    public List<ContactDto> getAllByPerson(PersonIdDto personIdDto) {
        List<Contact> contacts = contactRepository.getAllByPerson(mapId(personIdDto));
        return contactMapper.mapDtos(contacts);
    }

    @Override
    public List<ContactDto> getAll() {
        return contactMapper.mapDtos(contactRepository.getAll());
    }

    @Transactional
    @Override
    public ContactDto update(ContactDto contactDto, PersonIdDto personIdDto) {
        Contact contact = get(mapId(contactDto), mapId(personIdDto));
        contactMapper.update(contactDto, contact);
        return contactMapper.mapDto(contactRepository.save(contact, mapId(personIdDto)));
    }

    @Transactional
    @Override
    public ContactDto save(ContactDto contactDto, PersonIdDto personIdDto) {
        checkIsNew(contactDto);
        Contact contact = contactRepository.save(contactMapper.map(contactDto), mapId(personIdDto));
        return contactMapper.mapDto(contact);
    }

    @Override
    public void delete(ContactIdDto contactIdDto, PersonIdDto personIdDto) {
        int result = contactRepository.delete(mapId(contactIdDto), mapId(personIdDto));
        checkNotFoundWithId(result != 0, contactIdDto);
    }

    @Override
    public void deleteAllByPerson(PersonIdDto personIdDto) {
        checkNotFound(contactRepository.deleteAllByPerson(mapId(personIdDto)) != 0);
    }

    private Contact get(Long contactId, Long personId) {
        return getEntityFromOptional(contactRepository.get(contactId, personId), contactId);
    }
}
