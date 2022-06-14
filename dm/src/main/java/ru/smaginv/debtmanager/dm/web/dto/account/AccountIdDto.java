package ru.smaginv.debtmanager.dm.web.dto.account;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ru.smaginv.debtmanager.dm.web.dto.HasIdDto;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Getter
@Setter
@ToString
public class AccountIdDto implements HasIdDto {

    @NotBlank
    @Accessors(prefix = "account")
    private String accountId;

    @Override
    public boolean isNew() {
        return Objects.isNull(accountId);
    }
}
