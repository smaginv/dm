package ru.smaginv.debtmanager.web.dto.account;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.smaginv.debtmanager.web.dto.HasIdDto;
import ru.smaginv.debtmanager.web.dto.operation.OperationDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@JsonPropertyOrder(
        {"id", "type", "amount", "currency", "rate", "openDate", "closedDate", "active", "operations", "comment"}
)
public class AccountInfoDto implements HasIdDto {

    private String id;

    @NotBlank
    private String type;

    @NotBlank
    private String amount;

    @NotBlank
    private String currency;

    @NotBlank
    private String rate;

    @NotBlank
    @Size(max = 64)
    private String openDate;

    @Size(max = 64)
    private String closedDate;

    @Size(max = 512)
    private String comment;

    @NotBlank
    private String active;

    private List<OperationDto> operations;

    @Override
    public boolean isNew() {
        return Objects.isNull(id);
    }
}
