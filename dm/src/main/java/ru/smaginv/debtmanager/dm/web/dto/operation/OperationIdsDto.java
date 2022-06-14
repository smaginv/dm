package ru.smaginv.debtmanager.dm.web.dto.operation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.smaginv.debtmanager.dm.web.dto.HasIdDto;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Getter
@Setter
@ToString
public class OperationIdsDto implements HasIdDto {

    @NotBlank
    private String id;

    @NotBlank
    private String accountId;

    @Override
    public boolean isNew() {
        return Objects.isNull(id);
    }
}
