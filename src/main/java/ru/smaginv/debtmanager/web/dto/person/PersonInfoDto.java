package ru.smaginv.debtmanager.web.dto.person;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.smaginv.debtmanager.web.dto.HasIdDto;
import ru.smaginv.debtmanager.web.dto.account.AccountDto;
import ru.smaginv.debtmanager.web.dto.contact.ContactDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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

    @NotBlank
    @Size(min = 2, max = 32)
    private String firstName;

    @NotBlank
    @Size(min = 2, max = 32)
    private String lastName;

    @Size(max = 512)
    private String comment;

    private List<AccountDto> accounts;

    private List<ContactDto> contacts;

    @Override
    public boolean isNew() {
        return Objects.isNull(id);
    }
}
