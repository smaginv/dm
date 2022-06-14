package ru.smaginv.debtmanager.dm.web.dto.contact;

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
public class ContactIdDto implements HasIdDto {

    @NotBlank
    @Accessors(prefix = "contact")
    private String contactId;

    @Override
    public boolean isNew() {
        return Objects.isNull(contactId);
    }
}
