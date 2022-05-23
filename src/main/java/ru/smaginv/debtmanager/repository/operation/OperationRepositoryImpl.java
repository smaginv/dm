package ru.smaginv.debtmanager.repository.operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.smaginv.debtmanager.entity.account.Account;
import ru.smaginv.debtmanager.entity.operation.Operation;
import ru.smaginv.debtmanager.entity.operation.OperationType;
import ru.smaginv.debtmanager.repository.account.AccountRepositoryJpa;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class OperationRepositoryImpl implements OperationRepository {

    private final OperationRepositoryJpa operationRepository;
    private final AccountRepositoryJpa accountRepository;

    @Autowired
    public OperationRepositoryImpl(OperationRepositoryJpa operationRepository, AccountRepositoryJpa accountRepository) {
        this.operationRepository = operationRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public Optional<Operation> get(Long accountId, Long operationId) {
        return operationRepository.get(accountId, operationId);
    }

    @Override
    public List<Operation> getAllByAccount(Long accountId) {
        return operationRepository.getAllByAccount(accountId);
    }

    @Override
    public List<Operation> getAll() {
        return operationRepository.getAll();
    }

    @Override
    public List<Operation> getByType(Long accountId, OperationType operationType) {
        if (Objects.isNull(accountId))
            return operationRepository.getByType(operationType);
        return operationRepository.getByAccountAndType(accountId, operationType);
    }

    @Override
    public List<Operation> find(Long accountId, OperationType operationType,
                                LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return operationRepository.find(accountId, operationType, startDateTime, endDateTime);
    }

    @Override
    public Operation save(Long accountId, Operation operation) {
        Account account = accountRepository.getReferenceById(accountId);
        operation.setAccount(account);
        return operationRepository.save(operation);
    }

    @Override
    public int delete(Long accountId, Long operationId) {
        return operationRepository.delete(accountId, operationId);
    }

    @Override
    public int deleteAllByAccount(Long accountId) {
        return operationRepository.deleteAllByAccount(accountId);
    }
}
