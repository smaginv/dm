package ru.smaginv.debtmanager.web.dto.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.smaginv.debtmanager.entity.account.AccountType;
import ru.smaginv.debtmanager.util.validation.CurrencyValidator;
import ru.smaginv.debtmanager.util.validation.EnumValidator;
import ru.smaginv.debtmanager.web.dto.HasIdDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

@Getter
@Setter
@ToString
@JsonPropertyOrder({
        "id", "type", "amount", "currency", "rate", "openDate", "closedDate", "isActive", "comment"
})
@JsonIgnoreProperties(
        value = {"isActive"},
        allowGetters = true
)
public class AccountDto implements HasIdDto {

    private String id;

    @NotBlank
    @EnumValidator(enumClass = AccountType.class)
    private String type;

    @NotBlank
    private String amount;

    @CurrencyValidator
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

    private String isActive;

    @Override
    public boolean isNew() {
        return Objects.isNull(id);
    }
}