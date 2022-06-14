package ru.smaginv.debtmanager.repository.operation;

import ru.smaginv.debtmanager.entity.operation.Operation;
import ru.smaginv.debtmanager.entity.operation.OperationType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OperationRepository {

    Optional<Operation> get(Long userId, Long operationId);

    List<Operation> getAllByAccount(Long userId, Long accountId);

    List<Operation> getAll(Long userId);

    List<Operation> getByType(Long userId, Long accountId, OperationType operationType);

    List<Operation> find(Long userId, Long accountId, OperationType operationType,
                         LocalDateTime startDateTime, LocalDateTime endDateTime);

    void update(Operation operation);

    Operation create(Operation operation);

    int delete(Long userId, Long operationId);

    int deleteAllByAccount(Long userId, Long accountId);
}
