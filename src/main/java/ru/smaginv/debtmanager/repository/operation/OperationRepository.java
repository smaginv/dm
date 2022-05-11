package ru.smaginv.debtmanager.repository.operation;

import ru.smaginv.debtmanager.entity.operation.Operation;

import java.time.LocalDate;
import java.util.List;

public interface OperationRepository {

    Operation get(Long operationId, Long accountId);

    List<Operation> getAllByAccount(Long accountId);

    List<Operation> getAll();

    List<Operation> getAllLend();

    List<Operation> getAllLendByAccount(Long accountId);

    List<Operation> getAllLoan();

    List<Operation> getAllLoanByAccount(Long accountId);

    List<Operation> getBetweenDates(LocalDate startDate, LocalDate endDate);

    List<Operation> getBetweenDatesByAccount(LocalDate startDate, LocalDate endDate, Long accountId);

    List<Operation> getAllLendBetweenDates(LocalDate startDate, LocalDate endDate);

    List<Operation> getAllLoanBetweenDates(LocalDate startDate, LocalDate endDate);

    List<Operation> getAllLendBetweenDatesByAccount(LocalDate startDate, LocalDate endDate, Long accountId);

    List<Operation> getAllLoanBetweenDatesByAccount(LocalDate startDate, LocalDate endDate, Long accountId);

    Operation save(Operation operation, Long accountId);

    int delete(Long operationId, Long accountId);

    int deleteAllByAccount(Long accountId);
}
