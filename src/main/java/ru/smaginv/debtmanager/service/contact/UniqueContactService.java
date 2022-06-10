package ru.smaginv.debtmanager.service.contact;

import ru.smaginv.debtmanager.entity.contact.UniqueContact;

import java.util.List;

public interface UniqueContactService {

    void save(UniqueContact uniqueContact);

    void deleteByContactId(Long contactId);

    void deleteByContactIds(List<Long> contactIds);
}
