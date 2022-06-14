package ru.smaginv.debtmanager.dm.web.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.smaginv.debtmanager.dm.util.validation.EmailValidator;

@Getter
@Setter
@ToString
public class UserEmailDto {

    @EmailValidator
    private String email;
}
