package ru.smaginv.debtmanager.dm.web.dto.contact;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.smaginv.debtmanager.dm.web.dto.HasIdDto;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@ToString(callSuper = true)
@JsonPropertyOrder({
        "id", "type", "value"
})
public class ContactDto extends AbstractContactDto implements HasIdDto, Serializable {

    @JsonIgnoreProperties(allowGetters = true)
    private String id;

    @NotBlank
    @JsonIgnoreProperties(allowSetters = true)
    private String personId;

    @Override
    public boolean isNew() {
        return Objects.isNull(id);
    }
}
