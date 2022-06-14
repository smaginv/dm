package ru.smaginv.debtmanager.service.contact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.smaginv.debtmanager.entity.contact.UniqueContact;
import ru.smaginv.debtmanager.repository.contact.UniqueContactRepository;

import java.util.List;

@Service
public class UniqueContactServiceImpl implements UniqueContactService {

    private final UniqueContactRepository uniqueContactRepository;

    @Autowired
    public UniqueContactServiceImpl(UniqueContactRepository uniqueContactRepository) {
        this.uniqueContactRepository = uniqueContactRepository;
    }

    @Transactional
    @Override
    public void save(Long userId, UniqueContact uniqueContact) {
        uniqueContact.setUserId(userId);
        uniqueContactRepository.save(uniqueContact);
    }

    @Transactional
    @Override
    public void deleteByContactId(Long userId, Long contactId) {
        uniqueContactRepository.deleteByContactId(userId, contactId);
    }

    @Transactional
    @Override
    public void deleteByContactIds(Long userId, List<Long> contactIds) {
        uniqueContactRepository.deleteByContactIds(userId, contactIds);
    }
}
