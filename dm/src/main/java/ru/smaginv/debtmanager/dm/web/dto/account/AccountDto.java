package ru.smaginv.debtmanager.dm.web.dto.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.smaginv.debtmanager.dm.entity.account.AccountType;
import ru.smaginv.debtmanager.dm.util.validation.CurrencyValidator;
import ru.smaginv.debtmanager.dm.util.validation.EnumValidator;
import ru.smaginv.debtmanager.dm.web.dto.HasIdDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@ToString
@JsonPropertyOrder({
        "id", "type", "amount", "currency", "rate", "openDate", "closedDate", "status", "comment"
})
@JsonIgnoreProperties(
        value = {"id", "openDate", "closedDate", "status"},
        allowGetters = true
)
public class AccountDto implements HasIdDto, Serializable {

    private String id;

    @NotBlank
    @JsonIgnoreProperties(allowSetters = true)
    private String personId;

    @EnumValidator(enumClass = AccountType.class)
    private String type;

    @NotBlank
    private String amount;

    @CurrencyValidator
    private String currency;

    @NotBlank
    private String rate;

    private String openDate;

    private String closedDate;

    @Size(max = 512)
    private String comment;

    private String status;

    @Override
    public boolean isNew() {
        return Objects.isNull(id);
    }
}
