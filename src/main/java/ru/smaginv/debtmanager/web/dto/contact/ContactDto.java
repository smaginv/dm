package ru.smaginv.debtmanager.web.dto.contact;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.smaginv.debtmanager.entity.contact.ContactType;
import ru.smaginv.debtmanager.util.validation.EnumValidator;
import ru.smaginv.debtmanager.web.dto.HasIdDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

@Getter
@Setter
@ToString
@JsonPropertyOrder({
        "id", "type", "value"
})
public class ContactDto implements HasIdDto {

    private String id;

    @EnumValidator(enumClass = ContactType.class)
    private String type;

    @NotBlank
    @Size(min = 4, max = 128)
    private String value;

    @Override
    public boolean isNew() {
        return Objects.isNull(id);
    }
}
