package ru.smaginv.debtmanager.dm.web.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.smaginv.debtmanager.dm.entity.user.Role;
import ru.smaginv.debtmanager.dm.util.validation.EnumValidator;

@Getter
@Setter
@ToString
public class UserRoleDto {

    private String userId;

    @EnumValidator(enumClass = Role.class)
    private String role;
}
