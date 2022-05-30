package ru.smaginv.debtmanager.web.dto.account;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.smaginv.debtmanager.web.dto.HasIdDto;
import ru.smaginv.debtmanager.web.dto.operation.OperationDto;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@JsonPropertyOrder({
        "id", "type", "amount", "currency", "rate", "openDate", "closedDate", "status", "operations", "comment"
})
public class AccountInfoDto implements HasIdDto {

    private String id;

    private String type;

    private String amount;

    private String currency;

    private String rate;

    private String openDate;

    private String closedDate;

    private String comment;

    private String status;

    private List<OperationDto> operations;

    @Override
    public boolean isNew() {
        return Objects.isNull(id);
    }
}
