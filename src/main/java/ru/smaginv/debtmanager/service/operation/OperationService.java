package ru.smaginv.debtmanager.service.operation;

import ru.smaginv.debtmanager.web.dto.account.AccountDto;
import ru.smaginv.debtmanager.web.dto.account.AccountIdDto;
import ru.smaginv.debtmanager.web.dto.operation.*;

import java.util.List;

public interface OperationService {

    OperationDto get(OperationIdDto operationIdDto);

    List<OperationDto> getAllByAccount(AccountIdDto accountIdDto);

    List<OperationDto> getAll();

    List<OperationDto> getByType(OperationTypeDto operationTypeDto);

    List<OperationDto> find(OperationSearchDto operationSearchDto);

    void update(OperationUpdateDto operationUpdateDto);

    OperationDto create(OperationDto operationDto);

    AccountDto closeAccount(OperationDto operationDto);

    void delete(OperationIdsDto operationIdsDto);

    void deleteAllByAccount(AccountIdDto accountIdDto);
}
