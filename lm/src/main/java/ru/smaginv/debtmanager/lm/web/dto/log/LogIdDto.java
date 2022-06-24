package ru.smaginv.debtmanager.lm.web.dto.log;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class LogIdDto {

    @NotNull
    private Long logId;
}
