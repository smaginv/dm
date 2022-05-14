package ru.smaginv.debtmanager.web.dto.person;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.smaginv.debtmanager.web.dto.HasIdDto;

import java.util.Objects;

@Getter
@Setter
@ToString
public class PersonSearchDto implements HasIdDto {

    private String id;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String email;

    @Override
    public boolean isNew() {
        return Objects.isNull(id);
    }
}
