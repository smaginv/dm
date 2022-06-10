package ru.smaginv.debtmanager.web.dto.person;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.smaginv.debtmanager.web.dto.HasIdDto;
import ru.smaginv.debtmanager.web.dto.contact.ContactSearchDto;

import java.util.Objects;

@Getter
@Setter
@ToString
public class PersonSearchDto implements HasIdDto {

    private String id;

    private String firstName;

    private String lastName;

    ContactSearchDto contact;

    @Override
    public boolean isNew() {
        return Objects.isNull(id);
    }
}
