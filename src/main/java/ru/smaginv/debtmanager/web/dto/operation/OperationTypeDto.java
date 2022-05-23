package ru.smaginv.debtmanager.web.dto.operation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.smaginv.debtmanager.entity.operation.OperationType;
import ru.smaginv.debtmanager.util.validation.EnumValidator;

@Getter
@Setter
@ToString
public class OperationTypeDto {

    private Long accountId;

    @EnumValidator(enumClass = OperationType.class)
    private String type;
}
