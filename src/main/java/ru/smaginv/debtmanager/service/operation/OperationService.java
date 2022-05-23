package ru.smaginv.debtmanager.service.operation;

import ru.smaginv.debtmanager.web.dto.operation.OperationDto;
import ru.smaginv.debtmanager.web.dto.operation.OperationSearchDto;
import ru.smaginv.debtmanager.web.dto.operation.OperationTypeDto;

import java.util.List;

public interface OperationService {

    OperationDto get(Long accountId, Long operationId);

    List<OperationDto> getAllByAccount(Long accountId);

    List<OperationDto> getAll();

    List<OperationDto> getByType(OperationTypeDto operationTypeDto);

    List<OperationDto> find(OperationSearchDto operationSearchDto);

    void update(Long accountId, OperationDto operationDto);

    OperationDto create(Long accountId, OperationDto operationDto);

    void delete(Long accountId, Long operationId);

    void deleteAllByAccount(Long accountId);
}
