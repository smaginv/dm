package ru.smaginv.debtmanager.dm.web.dto.person;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.smaginv.debtmanager.dm.web.dto.HasIdDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@ToString
@JsonPropertyOrder({
        "id", "firstName", "lastName", "comment"
})
public class PersonDto implements HasIdDto, Serializable {

    @JsonIgnoreProperties(allowGetters = true)
    private String id;

    @NotBlank
    @Size(min = 2, max = 32)
    private String firstName;

    @NotBlank
    @Size(min = 2, max = 32)
    private String lastName;

    @Size(max = 512)
    private String comment;

    @Override
    public boolean isNew() {
        return Objects.isNull(id);
    }
}
