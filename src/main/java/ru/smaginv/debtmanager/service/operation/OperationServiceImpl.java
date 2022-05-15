package ru.smaginv.debtmanager.service.operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.smaginv.debtmanager.entity.operation.Operation;
import ru.smaginv.debtmanager.repository.operation.OperationRepository;
import ru.smaginv.debtmanager.web.dto.account.AccountIdDto;
import ru.smaginv.debtmanager.web.dto.operation.OperationDto;
import ru.smaginv.debtmanager.web.dto.operation.OperationIdDto;
import ru.smaginv.debtmanager.web.mapping.OperationMapper;

import java.time.LocalDate;
import java.util.List;

import static ru.smaginv.debtmanager.util.MappingUtil.mapId;
import static ru.smaginv.debtmanager.util.ValidationUtil.*;
import static ru.smaginv.debtmanager.util.entity.EntityUtil.getEntityFromOptional;

@Service
@Transactional(readOnly = true)
public class OperationServiceImpl implements OperationService {

    private final OperationRepository operationRepository;
    private final OperationMapper operationMapper;

    @Autowired
    public OperationServiceImpl(OperationRepository operationRepository, OperationMapper operationMapper) {
        this.operationRepository = operationRepository;
        this.operationMapper = operationMapper;
    }

    @Override
    public OperationDto get(OperationIdDto operationIdDto, AccountIdDto accountIdDto) {
        Operation operation = get(mapId(operationIdDto), mapId(accountIdDto));
        return operationMapper.mapDto(operation);
    }

    @Override
    public List<OperationDto> getAllByAccount(AccountIdDto accountIdDto) {
        List<Operation> operations = operationRepository.getAllByAccount(mapId(accountIdDto));
        return operationMapper.mapDtos(operations);
    }

    @Override
    public List<OperationDto> getAll() {
        return operationMapper.mapDtos(operationRepository.getAll());
    }

    @Override
    public List<OperationDto> getAllLend() {
        return operationMapper.mapDtos(operationRepository.getAllLend());
    }

    @Override
    public List<OperationDto> getAllLendByAccount(AccountIdDto accountIdDto) {
        List<Operation> operations = operationRepository.getAllLendByAccount(mapId(accountIdDto));
        return operationMapper.mapDtos(operations);
    }

    @Override
    public List<OperationDto> getAllLoan() {
        return operationMapper.mapDtos(operationRepository.getAllLoan());
    }

    @Override
    public List<OperationDto> getAllLoanByAccount(AccountIdDto accountIdDto) {
        List<Operation> operations = operationRepository.getAllLoanByAccount(mapId(accountIdDto));
        return operationMapper.mapDtos(operations);
    }

    @Override
    public List<OperationDto> getBetweenDates(LocalDate startDate, LocalDate endDate) {
        return operationMapper.mapDtos(operationRepository.getBetweenDates(startDate, endDate));
    }

    @Override
    public List<OperationDto> getBetweenDatesByAccount(LocalDate startDate, LocalDate endDate,
                                                       AccountIdDto accountIdDto) {
        Long accountId = mapId(accountIdDto);
        List<Operation> operations = operationRepository.getBetweenDatesByAccount(startDate, endDate, accountId);
        return operationMapper.mapDtos(operations);
    }

    @Override
    public List<OperationDto> getAllLendBetweenDates(LocalDate startDate, LocalDate endDate) {
        return operationMapper.mapDtos(operationRepository.getAllLendBetweenDates(startDate, endDate));
    }

    @Override
    public List<OperationDto> getAllLoanBetweenDates(LocalDate startDate, LocalDate endDate) {
        return operationMapper.mapDtos(operationRepository.getAllLoanBetweenDates(startDate, endDate));
    }

    @Override
    public List<OperationDto> getAllLendBetweenDatesByAccount(LocalDate startDate, LocalDate endDate,
                                                              AccountIdDto accountIdDto) {
        Long accountId = mapId(accountIdDto);
        List<Operation> operations = operationRepository.getAllLendBetweenDatesByAccount(startDate, endDate, accountId);
        return operationMapper.mapDtos(operations);
    }

    @Override
    public List<OperationDto> getAllLoanBetweenDatesByAccount(LocalDate startDate, LocalDate endDate,
                                                              AccountIdDto accountIdDto) {
        Long accountId = mapId(accountIdDto);
        List<Operation> operations = operationRepository.getAllLoanBetweenDatesByAccount(startDate, endDate, accountId);
        return operationMapper.mapDtos(operations);
    }

    @Transactional
    @Override
    public OperationDto update(OperationDto operationDto, AccountIdDto accountIdDto) {
        Operation operation = get(mapId(operationDto), mapId(accountIdDto));
        operationMapper.update(operationDto, operation);
        return operationMapper.mapDto(operationRepository.save(operation, mapId(accountIdDto)));
    }

    @Transactional
    @Override
    public OperationDto save(OperationDto operationDto, AccountIdDto accountIdDto) {
        checkIsNew(operationDto);
        Operation operation = operationMapper.map(operationDto);
        operation = operationRepository.save(operation, mapId(accountIdDto));
        return operationMapper.mapDto(operation);
    }

    @Override
    public void delete(OperationIdDto operationIdDto, AccountIdDto accountIdDto) {
        int result = operationRepository.delete(mapId(operationIdDto), mapId(accountIdDto));
        checkNotFoundWithId(result != 0, operationIdDto);
    }

    @Override
    public void deleteAllByAccount(AccountIdDto accountIdDto) {
        checkNotFound(operationRepository.deleteAllByAccount(mapId(accountIdDto)) != 0);
    }

    private Operation get(Long operationId, Long personId) {
        return getEntityFromOptional(operationRepository.get(operationId, personId), operationId);
    }
}
