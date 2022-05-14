package ru.smaginv.debtmanager.web.dto.contact;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.smaginv.debtmanager.web.dto.HasIdDto;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Getter
@Setter
@ToString
public class ContactDto implements HasIdDto {

    private String id;

    @NotBlank
    private String type;

    @NotBlank
    private String value;

    @Override
    public boolean isNew() {
        return Objects.isNull(id);
    }
}
