package ru.smaginv.debtmanager.web.dto.person;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.smaginv.debtmanager.entity.HasId;
import ru.smaginv.debtmanager.entity.account.Account;
import ru.smaginv.debtmanager.entity.contact.Contact;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
public class PersonInfoDto implements HasId<String> {

    private String id;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String email;

    private String comment;

    private List<Account> accounts;

    private List<Contact> contacts;

    @Override
    public boolean isNew() {
        return Objects.isNull(id);
    }
}
