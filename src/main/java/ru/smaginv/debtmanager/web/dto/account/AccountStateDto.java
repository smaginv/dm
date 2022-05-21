package ru.smaginv.debtmanager.web.dto.account;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.smaginv.debtmanager.util.validation.BooleanValidator;

@Getter
@Setter
@ToString
public class AccountStateDto {

    private Long personId;

    @BooleanValidator
    private String isActive;
}
