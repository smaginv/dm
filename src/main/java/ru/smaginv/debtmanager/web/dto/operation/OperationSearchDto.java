package ru.smaginv.debtmanager.web.dto.operation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.smaginv.debtmanager.entity.operation.OperationType;
import ru.smaginv.debtmanager.util.validation.EnumValidator;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class OperationSearchDto {

    private LocalDate startDate;

    private LocalDate endDate;

    private Long accountId;

    @EnumValidator(enumClass = OperationType.class)
    private String type;
}
