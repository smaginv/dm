package ru.smaginv.debtmanager.web.dto.contact;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.smaginv.debtmanager.web.dto.HasIdDto;

import java.util.Objects;

@Getter
@Setter
@ToString
public class ContactIdDto implements HasIdDto {

    private String id;

    @Override
    public boolean isNew() {
        return Objects.isNull(id);
    }
}
