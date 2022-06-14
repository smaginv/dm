package ru.smaginv.debtmanager.web.dto.operation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ru.smaginv.debtmanager.web.dto.HasIdDto;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Getter
@Setter
@ToString
public class OperationIdDto implements HasIdDto {

    @NotBlank
    @Accessors(prefix = "operation")
    private String operationId;

    @Override
    public boolean isNew() {
        return Objects.isNull(operationId);
    }
}
