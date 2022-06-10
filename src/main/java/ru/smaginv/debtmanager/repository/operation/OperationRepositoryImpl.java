package ru.smaginv.debtmanager.repository.operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.smaginv.debtmanager.entity.operation.Operation;
import ru.smaginv.debtmanager.entity.operation.OperationType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static ru.smaginv.debtmanager.util.AppUtil.getAuthUserId;

@Repository
public class OperationRepositoryImpl implements OperationRepository {

    private final OperationRepositoryJpa operationRepository;

    @Autowired
    public OperationRepositoryImpl(OperationRepositoryJpa operationRepository) {
        this.operationRepository = operationRepository;
    }

    @Override
    public Optional<Operation> get(Long operationId) {
        return operationRepository.get(operationId, getAuthUserId());
    }

    @Override
    public List<Operation> getAllByAccount(Long accountId) {
        return operationRepository.getAllByAccount(accountId, getAuthUserId());
    }

    @Override
    public List<Operation> getAll() {
        return operationRepository.getAll(getAuthUserId());
    }

    @Override
    public List<Operation> getByType(Long accountId, OperationType operationType) {
        if (Objects.isNull(accountId))
            return operationRepository.getByType(operationType, getAuthUserId());
        return operationRepository.getByAccountAndType(accountId, operationType, getAuthUserId());
    }

    @Override
    public List<Operation> find(Long accountId, OperationType operationType,
                                LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return operationRepository.find(accountId, operationType, startDateTime, endDateTime, getAuthUserId());
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
    public int delete(Long operationId) {
        return operationRepository.delete(operationId, getAuthUserId());
    }

    @Override
    public int deleteAllByAccount(Long accountId) {
        return operationRepository.deleteAllByAccount(accountId, getAuthUserId());
    }
}
