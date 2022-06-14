package ru.smaginv.debtmanager.dm.service.operation;

import ru.smaginv.debtmanager.dm.web.dto.account.AccountDto;
import ru.smaginv.debtmanager.dm.web.dto.account.AccountIdDto;
import ru.smaginv.debtmanager.dm.web.dto.operation.*;

import java.util.List;

public interface OperationService {

    OperationDto get(Long userId, OperationIdDto operationIdDto);

    List<OperationDto> getAllByAccount(Long userId, AccountIdDto accountIdDto);

    List<OperationDto> getAll(Long userId);

    List<OperationDto> getByType(Long userId, OperationTypeDto operationTypeDto);

    List<OperationDto> find(Long userId, OperationSearchDto operationSearchDto);

    void update(Long userId, OperationUpdateDto operationUpdateDto);

    OperationDto create(Long userId, OperationDto operationDto);

    AccountDto closeAccount(Long userId, OperationDto operationDto);

    void delete(Long userId, OperationIdsDto operationIdsDto);

    void deleteAllByAccount(Long userId, AccountIdDto accountIdDto);
}
