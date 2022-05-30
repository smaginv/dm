package ru.smaginv.debtmanager.web.dto.person;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.smaginv.debtmanager.web.dto.HasIdDto;

import javax.validation.constraints.Size;
import java.util.Objects;

@Getter
@Setter
@ToString
@JsonPropertyOrder({
        "id", "firstName", "lastName", "comment"
})
public class PersonDto implements HasIdDto {

    private String id;

    @Size(min = 2, max = 32)
    private String firstName;

    @Size(min = 2, max = 32)
    private String lastName;

    @Size(max = 512)
    private String comment;

    @Override
    public boolean isNew() {
        return Objects.isNull(id);
    }
}
