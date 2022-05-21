package ru.smaginv.debtmanager.web.dto.account;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.smaginv.debtmanager.entity.account.AccountType;
import ru.smaginv.debtmanager.util.validation.EnumValidator;

@Getter
@Setter
@ToString
public class AccountTypeDto {

    @EnumValidator(enumClass = AccountType.class)
    private String type;
}
