package ru.smaginv.debtmanager.service.contact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.smaginv.debtmanager.entity.contact.UniqueContact;
import ru.smaginv.debtmanager.repository.contact.UniqueContactRepository;

import java.util.List;

import static ru.smaginv.debtmanager.util.AppUtil.getAuthUserId;

@Service
public class UniqueContactServiceImpl implements UniqueContactService {

    private final UniqueContactRepository uniqueContactRepository;

    @Autowired
    public UniqueContactServiceImpl(UniqueContactRepository uniqueContactRepository) {
        this.uniqueContactRepository = uniqueContactRepository;
    }

    @Transactional
    @Override
    public void save(UniqueContact uniqueContact) {
        uniqueContact.setUserId(getAuthUserId());
        uniqueContactRepository.save(uniqueContact);
    }

    @Transactional
    @Override
    public void deleteByContactId(Long contactId) {
        uniqueContactRepository.deleteByContactId(contactId, getAuthUserId());
    }

    @Transactional
    @Override
    public void deleteByContactIds(List<Long> contactIds) {
        uniqueContactRepository.deleteByContactIds(contactIds, getAuthUserId());
    }
}
