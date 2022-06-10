package ru.smaginv.debtmanager.web.dto.account;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.smaginv.debtmanager.entity.account.AccountStatus;
import ru.smaginv.debtmanager.util.validation.EnumValidator;

@Getter
@Setter
@ToString
public class AccountStatusDto {

    @EnumValidator(enumClass = AccountStatus.class)
    private String status;
}
