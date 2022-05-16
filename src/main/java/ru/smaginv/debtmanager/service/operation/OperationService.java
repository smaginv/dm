package ru.smaginv.debtmanager.service.operation;

import ru.smaginv.debtmanager.web.dto.operation.OperationDto;

import java.time.LocalDate;
import java.util.List;

public interface OperationService {

    OperationDto get(Long operationId, Long accountId);

    List<OperationDto> getAllByAccount(Long accountId);

    List<OperationDto> getAll();

    List<OperationDto> getAllLend();

    List<OperationDto> getAllLendByAccount(Long accountId);

    List<OperationDto> getAllLoan();

    List<OperationDto> getAllLoanByAccount(Long accountId);

    List<OperationDto> getBetweenDates(LocalDate startDate, LocalDate endDate);

    List<OperationDto> getBetweenDatesByAccount(LocalDate startDate, LocalDate endDate,
                                                Long accountId);

    List<OperationDto> getAllLendBetweenDates(LocalDate startDate, LocalDate endDate);

    List<OperationDto> getAllLoanBetweenDates(LocalDate startDate, LocalDate endDate);

    List<OperationDto> getAllLendBetweenDatesByAccount(LocalDate startDate, LocalDate endDate,
                                                       Long accountId);

    List<OperationDto> getAllLoanBetweenDatesByAccount(LocalDate startDate, LocalDate endDate,
                                                       Long accountId);

    OperationDto update(OperationDto operationDto, Long accountId);

    OperationDto create(OperationDto operationDto, Long accountId);

    void delete(Long operationId, Long accountId);

    void deleteAllByAccount(Long accountId);
}
