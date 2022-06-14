package ru.smaginv.debtmanager.dm.web.dto.person;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ru.smaginv.debtmanager.dm.web.dto.HasIdDto;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Getter
@Setter
@ToString
public class PersonIdDto implements HasIdDto {

    @NotBlank
    @Accessors(prefix = "person")
    private String personId;

    @Override
    public boolean isNew() {
        return Objects.isNull(personId);
    }
}
