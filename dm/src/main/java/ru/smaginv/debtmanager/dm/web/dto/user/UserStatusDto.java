package ru.smaginv.debtmanager.dm.web.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.smaginv.debtmanager.dm.entity.user.Status;
import ru.smaginv.debtmanager.dm.util.validation.EnumValidator;

@Getter
@Setter
@ToString
public class UserStatusDto {

    private String userId;

    @EnumValidator(enumClass = Status.class)
    private String status;
}
