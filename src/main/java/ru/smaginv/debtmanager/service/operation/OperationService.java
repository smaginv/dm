package ru.smaginv.debtmanager.service.operation;

import ru.smaginv.debtmanager.web.dto.account.AccountIdDto;
import ru.smaginv.debtmanager.web.dto.operation.OperationDto;
import ru.smaginv.debtmanager.web.dto.operation.OperationIdDto;

import java.time.LocalDate;
import java.util.List;

public interface OperationService {

    OperationDto get(OperationIdDto operationIdDto, AccountIdDto accountIdDto);

    List<OperationDto> getAllByAccount(AccountIdDto accountIdDto);

    List<OperationDto> getAll();

    List<OperationDto> getAllLend();

    List<OperationDto> getAllLendByAccount(AccountIdDto accountIdDto);

    List<OperationDto> getAllLoan();

    List<OperationDto> getAllLoanByAccount(AccountIdDto accountIdDto);

    List<OperationDto> getBetweenDates(LocalDate startDate, LocalDate endDate);

    List<OperationDto> getBetweenDatesByAccount(LocalDate startDate, LocalDate endDate,
                                                AccountIdDto accountIdDto);

    List<OperationDto> getAllLendBetweenDates(LocalDate startDate, LocalDate endDate);

    List<OperationDto> getAllLoanBetweenDates(LocalDate startDate, LocalDate endDate);

    List<OperationDto> getAllLendBetweenDatesByAccount(LocalDate startDate, LocalDate endDate,
                                                       AccountIdDto accountIdDto);

    List<OperationDto> getAllLoanBetweenDatesByAccount(LocalDate startDate, LocalDate endDate,
                                                       AccountIdDto accountIdDto);

    OperationDto update(OperationDto operationDto, AccountIdDto accountIdDto);

    OperationDto save(OperationDto operationDto, AccountIdDto accountIdDto);

    void delete(OperationIdDto operationIdDto, AccountIdDto accountIdDto);

    void deleteAllByAccount(AccountIdDto accountIdDto);
}
