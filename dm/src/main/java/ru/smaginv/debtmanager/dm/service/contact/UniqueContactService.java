package ru.smaginv.debtmanager.dm.service.contact;

import ru.smaginv.debtmanager.dm.entity.contact.UniqueContact;

import java.util.List;

public interface UniqueContactService {

    void save(Long userId, UniqueContact uniqueContact);

    void deleteByContactId(Long userId, Long contactId);

    void deleteByContactIds(Long userId, List<Long> contactIds);
}
