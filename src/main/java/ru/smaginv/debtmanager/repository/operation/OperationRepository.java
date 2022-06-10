package ru.smaginv.debtmanager.repository.operation;

import ru.smaginv.debtmanager.entity.operation.Operation;
import ru.smaginv.debtmanager.entity.operation.OperationType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OperationRepository {

    Optional<Operation> get(Long operationId);

    List<Operation> getAllByAccount(Long accountId);

    List<Operation> getAll();

    List<Operation> getByType(Long accountId, OperationType operationType);

    List<Operation> find(Long accountId, OperationType operationType,
                         LocalDateTime startDateTime, LocalDateTime endDateTime);

    void update(Operation operation);

    Operation create(Operation operation);

    int delete(Long operationId);

    int deleteAllByAccount(Long accountId);
}
