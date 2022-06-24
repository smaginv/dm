package ru.smaginv.debtmanager.lm.web.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.smaginv.debtmanager.lm.util.validation.EmailValidator;

@Getter
@Setter
@ToString
public class UserEmailDto {

    @EmailValidator
    private String email;
}
