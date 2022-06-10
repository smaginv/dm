package ru.smaginv.debtmanager.web.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.smaginv.debtmanager.entity.user.Role;
import ru.smaginv.debtmanager.util.validation.EnumValidator;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class UserRoleDto {

    @NotBlank
    private String userId;

    @EnumValidator(enumClass = Role.class)
    private String role;
}
