package ru.smaginv.debtmanager.repository.operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.smaginv.debtmanager.entity.account.Account;
import ru.smaginv.debtmanager.entity.operation.Operation;
import ru.smaginv.debtmanager.repository.account.AccountRepositoryJpa;

import java.time.LocalDate;
import java.util.List;
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
    public Optional<Operation> get(Long operationId, Long accountId) {
        return operationRepository.get(operationId, accountId);
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
    public List<Operation> getAllLend() {
        return operationRepository.getAllLend();
    }

    @Override
    public List<Operation> getAllLendByAccount(Long accountId) {
        return operationRepository.getAllLendByAccount(accountId);
    }

    @Override
    public List<Operation> getAllLoan() {
        return operationRepository.getAllLoan();
    }

    @Override
    public List<Operation> getAllLoanByAccount(Long accountId) {
        return operationRepository.getAllLoanByAccount(accountId);
    }

    @Override
    public List<Operation> getBetweenDates(LocalDate startDate, LocalDate endDate) {
        return operationRepository.getBetweenDates(startDate, endDate);
    }

    @Override
    public List<Operation> getBetweenDatesByAccount(LocalDate startDate, LocalDate endDate, Long accountId) {
        return operationRepository.getBetweenDatesByAccount(startDate, endDate, accountId);
    }

    @Override
    public List<Operation> getAllLendBetweenDates(LocalDate startDate, LocalDate endDate) {
        return operationRepository.getAllLendBetweenDates(startDate, endDate);
    }

    @Override
    public List<Operation> getAllLoanBetweenDates(LocalDate startDate, LocalDate endDate) {
        return operationRepository.getAllLoanBetweenDates(startDate, endDate);
    }

    @Override
    public List<Operation> getAllLendBetweenDatesByAccount(LocalDate startDate, LocalDate endDate, Long accountId) {
        return operationRepository.getAllLendBetweenDatesByAccount(startDate, endDate, accountId);
    }

    @Override
    public List<Operation> getAllLoanBetweenDatesByAccount(LocalDate startDate, LocalDate endDate, Long accountId) {
        return operationRepository.getAllLoanBetweenDatesByAccount(startDate, endDate, accountId);
    }

    @Override
    public Operation save(Operation operation, Long accountId) {
        if (!operation.isNew() && get(operation.getId(), accountId).isEmpty())
            return null;
        Account account = accountRepository.getById(accountId);
        operation.setAccount(account);
        return operationRepository.save(operation);
    }

    @Override
    public int delete(Long operationId, Long accountId) {
        return operationRepository.delete(operationId, accountId);
    }

    @Override
    public int deleteAllByAccount(Long accountId) {
        return operationRepository.deleteAllByAccount(accountId);
    }
}
