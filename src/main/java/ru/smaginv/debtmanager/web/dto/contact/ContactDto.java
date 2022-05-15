package ru.smaginv.debtmanager.web.dto.contact;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.smaginv.debtmanager.web.dto.HasIdDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

@Getter
@Setter
@ToString
public class ContactDto implements HasIdDto {

    private String id;

    @NotBlank
    @Size(max = 5)
    private String type;

    @NotBlank
    @Size(max = 128)
    private String value;

    @Override
    public boolean isNew() {
        return Objects.isNull(id);
    }
}
