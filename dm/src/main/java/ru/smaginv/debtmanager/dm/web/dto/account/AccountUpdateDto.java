package ru.smaginv.debtmanager.dm.web.dto.account;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.smaginv.debtmanager.dm.web.dto.HasIdDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

@Getter
@Setter
@ToString
public class AccountUpdateDto implements HasIdDto {

    @NotBlank
    private String id;

    @Size(max = 4)
    private String rate;

    @Size(max = 512)
    private String comment;

    @Override
    public boolean isNew() {
        return Objects.isNull(id);
    }
}
