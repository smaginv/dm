package ru.smaginv.debtmanager.dm.web.dto.contact;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.smaginv.debtmanager.dm.entity.contact.ContactType;
import ru.smaginv.debtmanager.dm.util.validation.EnumValidator;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
public abstract class AbstractContactDto {

    @EnumValidator(enumClass = ContactType.class)
    private String type;

    @NotBlank
    @Size(min = 4, max = 128)
    private String value;
}
