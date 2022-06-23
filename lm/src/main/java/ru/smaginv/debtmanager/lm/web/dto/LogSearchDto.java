package ru.smaginv.debtmanager.lm.web.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Getter
@Setter
@ToString
public class LogSearchDto {

    @NotNull
    @PastOrPresent
    private LocalDate date;

    private String username;

    private String requestURI;

    private String method;
}
