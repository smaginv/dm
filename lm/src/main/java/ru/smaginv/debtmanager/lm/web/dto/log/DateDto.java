package ru.smaginv.debtmanager.lm.web.dto.log;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Getter
@Setter
@ToString
public class DateDto {

    @NotNull
    @PastOrPresent
    private LocalDate date;
}
