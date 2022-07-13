package ru.smaginv.debtmanager.lm.web.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class UserIdDto {

    @NotNull
    private Long id;
}
