package ru.smaginv.debtmanager.dm.web.dto.account;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.smaginv.debtmanager.dm.entity.account.AccountStatus;
import ru.smaginv.debtmanager.dm.util.validation.EnumValidator;

@Getter
@Setter
@ToString
public class AccountStatusDto {

    @EnumValidator(enumClass = AccountStatus.class)
    private String status;
}
