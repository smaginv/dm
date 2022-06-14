package ru.smaginv.debtmanager.dm.web.dto.person;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.smaginv.debtmanager.dm.web.dto.HasIdDto;
import ru.smaginv.debtmanager.dm.web.dto.account.AccountDto;
import ru.smaginv.debtmanager.dm.web.dto.contact.ContactDto;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@JsonPropertyOrder({
        "id", "firstName", "lastName", "contacts", "accounts", "comment"
})
public class PersonInfoDto implements HasIdDto {

    private String id;

    private String firstName;

    private String lastName;

    private String comment;

    private List<AccountDto> accounts;

    private List<ContactDto> contacts;

    @Override
    public boolean isNew() {
        return Objects.isNull(id);
    }
}
