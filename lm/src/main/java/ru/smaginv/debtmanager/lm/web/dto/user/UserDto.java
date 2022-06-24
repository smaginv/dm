package ru.smaginv.debtmanager.lm.web.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.smaginv.debtmanager.lm.entity.user.Role;
import ru.smaginv.debtmanager.lm.util.validation.EmailValidator;
import ru.smaginv.debtmanager.lm.util.validation.EnumValidator;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@JsonPropertyOrder({
        "id", "username", "email", "role"
})
@JsonIgnoreProperties(
        value = {"id"},
        allowGetters = true
)
public class UserDto {

    private String id;

    @NotBlank
    @Size(min = 2, max = 32)
    private String username;

    @EmailValidator
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank
    @Size(min = 8, max = 256)
    private String password;

    @EnumValidator(enumClass = Role.class)
    private String role;
}
