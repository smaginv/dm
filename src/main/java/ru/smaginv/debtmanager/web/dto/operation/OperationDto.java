package ru.smaginv.debtmanager.web.dto.operation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.smaginv.debtmanager.web.dto.HasIdDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

@Getter
@Setter
@ToString
public class OperationDto implements HasIdDto {

    private String id;

    @NotBlank
    @Size(max = 4)
    private String type;

    @NotBlank
    @Size(max = 64)
    private String operDate;

    @NotBlank
    private String amount;

    @NotBlank
    @Size(max = 512)
    private String description;

    @Override
    public boolean isNew() {
        return Objects.isNull(id);
    }
}
