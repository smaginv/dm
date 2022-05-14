package ru.smaginv.debtmanager.web.dto.person;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class PersonIdDto {

    @NotBlank
    private String id;
}
