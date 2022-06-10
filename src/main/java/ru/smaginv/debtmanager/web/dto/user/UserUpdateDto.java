package ru.smaginv.debtmanager.web.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.smaginv.debtmanager.util.validation.EmailValidator;
import ru.smaginv.debtmanager.web.dto.HasIdDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

@Getter
@Setter
@ToString
public class UserUpdateDto implements HasIdDto {

    @NotBlank
    private String id;

    @NotBlank
    @Size(min = 2, max = 32)
    private String firstName;

    @NotBlank
    @Size(min = 2, max = 32)
    private String lastName;

    @NotBlank
    @Size(min = 2, max = 32)
    private String username;

    @EmailValidator
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank
    @Size(min = 8, max = 256)
    private String password;

    @Override
    public boolean isNew() {
        return Objects.isNull(id);
    }
}
