package ru.smaginv.debtmanager.web.dto.person;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.smaginv.debtmanager.entity.account.Account;
import ru.smaginv.debtmanager.web.dto.HasIdDto;
import ru.smaginv.debtmanager.web.dto.contact.ContactDto;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
public class PersonInfoDto implements HasIdDto {

    private String id;

    private String firstName;

    private String lastName;

    private String comment;

    private List<Account> accounts;

    private List<ContactDto> contacts;

    @Override
    public boolean isNew() {
        return Objects.isNull(id);
    }
}
