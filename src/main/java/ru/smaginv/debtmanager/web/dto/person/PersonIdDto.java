package ru.smaginv.debtmanager.web.dto.person;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.smaginv.debtmanager.web.dto.HasIdDto;

import java.util.Objects;

@Getter
@Setter
@ToString
public class PersonIdDto implements HasIdDto {

    private String id;

    @Override
    public boolean isNew() {
        return Objects.isNull(id);
    }
}
