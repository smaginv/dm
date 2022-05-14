package ru.smaginv.debtmanager.web.dto.person;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PersonSearchDto {

    private String id;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String email;
}
