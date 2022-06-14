package ru.smaginv.debtmanager.service.contact;

import ru.smaginv.debtmanager.entity.contact.UniqueContact;

import java.util.List;

public interface UniqueContactService {

    void save(Long userId, UniqueContact uniqueContact);

    void deleteByContactId(Long userId, Long contactId);

    void deleteByContactIds(Long userId, List<Long> contactIds);
}
