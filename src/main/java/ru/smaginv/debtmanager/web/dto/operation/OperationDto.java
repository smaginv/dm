package ru.smaginv.debtmanager.web.dto.operation;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.smaginv.debtmanager.entity.operation.OperationType;
import ru.smaginv.debtmanager.util.validation.EnumValidator;
import ru.smaginv.debtmanager.web.dto.HasIdDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

@Getter
@Setter
@ToString
@JsonPropertyOrder({
        "id", "type", "operDate", "amount", "description"
})
public class OperationDto implements HasIdDto {

    private String id;

    @EnumValidator(enumClass = OperationType.class)
    private String type;

    @Size(max = 64)
    private String operDate;

    @NotBlank
    @Size(max = 16)
    private String amount;

    @Size(max = 512)
    private String description;

    @Override
    public boolean isNew() {
        return Objects.isNull(id);
    }
}
