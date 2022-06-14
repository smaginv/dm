package ru.smaginv.debtmanager.dm.web.dto.operation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class OperationUpdateDto implements HasIdDto {

    @NotBlank
    private String id;

    @NotBlank
    @JsonIgnoreProperties(allowSetters = true)
    private String accountId;

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
