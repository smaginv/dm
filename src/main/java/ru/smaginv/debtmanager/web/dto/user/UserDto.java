package ru.smaginv.debtmanager.web.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.smaginv.debtmanager.util.validation.EmailValidator;
import ru.smaginv.debtmanager.web.dto.HasIdDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@JsonPropertyOrder({
        "id", "name", "email", "status", "roles"
})
@JsonIgnoreProperties(
        value = {"status", "roles"},
        allowGetters = true
)
public class UserDto implements HasIdDto {

    private String id;

    @NotBlank
    @Size(min = 2, max = 32)
    private String name;

    @EmailValidator
    private String email;

    @NotBlank
    @Size(min = 4, max = 256)
    private String password;

    private String status;

    private List<String> roles;

    @Override
    public boolean isNew() {
        return Objects.isNull(id);
    }
}
