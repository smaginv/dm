package ru.smaginv.debtmanager.dm.web.dto.contact;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.smaginv.debtmanager.dm.web.dto.HasIdDto;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Getter
@Setter
@ToString
public class ContactIdDto implements HasIdDto {

    @NotBlank
    private String id;

    @Override
    public boolean isNew() {
        return Objects.isNull(id);
    }
}
