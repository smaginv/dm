package ru.smaginv.debtmanager.dm.web.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.smaginv.debtmanager.dm.util.validation.EmailValidator;
import ru.smaginv.debtmanager.dm.web.dto.HasIdDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@JsonPropertyOrder({
        "id", "firstName", "lastName", "username", "email", "status", "roles"
})
@JsonIgnoreProperties(
        value = {"id", "status", "roles"},
        allowGetters = true
)
public class UserDto implements HasIdDto {

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

    private String status;

    private Set<String> roles;

    @Override
    public boolean isNew() {
        return Objects.isNull(id);
    }
}
