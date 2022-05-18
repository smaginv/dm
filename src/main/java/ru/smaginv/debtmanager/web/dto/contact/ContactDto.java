package ru.smaginv.debtmanager.web.dto.contact;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.smaginv.debtmanager.web.dto.HasIdDto;

import java.util.Objects;

@Getter
@Setter
@ToString(callSuper = true)
@JsonPropertyOrder({"id", "type", "value"})
public class ContactDto extends AbstractContactDto implements HasIdDto {

    private String id;

    @Override
    public boolean isNew() {
        return Objects.isNull(id);
    }
}
