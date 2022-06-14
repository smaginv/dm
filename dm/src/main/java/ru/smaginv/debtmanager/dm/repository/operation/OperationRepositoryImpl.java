package ru.smaginv.debtmanager.dm.repository.operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.smaginv.debtmanager.dm.entity.operation.Operation;
import ru.smaginv.debtmanager.dm.entity.operation.OperationType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class OperationRepositoryImpl implements OperationRepository {

    private final OperationRepositoryJpa operationRepository;

    @Autowired
    public OperationRepositoryImpl(OperationRepositoryJpa operationRepository) {
        this.operationRepository = operationRepository;
    }

    @Override
    public Optional<Operation> get(Long userId, Long operationId) {
        return operationRepository.get(userId, operationId);
    }

    @Override
    public List<Operation> getAllByAccount(Long userId, Long accountId) {
        return operationRepository.getAllByAccount(userId, accountId);
    }

    @Override
    public List<Operation> getAll(Long userId) {
        return operationRepository.getAll(userId);
    }

    @Override
    public List<Operation> getByType(Long userId, Long accountId, OperationType operationType) {
        if (Objects.isNull(accountId))
            return operationRepository.getByType(userId, operationType);
        return operationRepository.getByAccountAndType(userId, accountId, operationType);
    }

    @Override
    public List<Operation> find(Long userId, Long accountId, OperationType operationType,
                                LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return operationRepository.find(userId, accountId, operationType, startDateTime, endDateTime);
    }

    @Override
    public void update(Operation operation) {
        operationRepository.save(operation);
    }

    @Override
    public Operation create(Operation operation) {
        return operationRepository.save(operation);
    }

    @Override
    public int delete(Long userId, Long operationId) {
        return operationRepository.delete(userId, operationId);
    }

    @Override
    public int deleteAllByAccount(Long userId, Long accountId) {
        return operationRepository.deleteAllByAccount(userId, accountId);
    }
}
