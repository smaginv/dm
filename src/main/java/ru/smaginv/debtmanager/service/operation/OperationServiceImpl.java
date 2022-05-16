package ru.smaginv.debtmanager.service.operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.smaginv.debtmanager.entity.operation.Operation;
import ru.smaginv.debtmanager.repository.operation.OperationRepository;
import ru.smaginv.debtmanager.util.MappingUtil;
import ru.smaginv.debtmanager.util.ValidationUtil;
import ru.smaginv.debtmanager.web.dto.operation.OperationDto;
import ru.smaginv.debtmanager.web.mapping.OperationMapper;

import java.time.LocalDate;
import java.util.List;

import static ru.smaginv.debtmanager.util.entity.EntityUtil.getEntityFromOptional;

@Service
@Transactional(readOnly = true)
public class OperationServiceImpl implements OperationService {

    private final OperationRepository operationRepository;
    private final OperationMapper operationMapper;
    private final ValidationUtil validationUtil;
    private final MappingUtil mappingUtil;

    @Autowired
    public OperationServiceImpl(OperationRepository operationRepository, OperationMapper operationMapper,
                                ValidationUtil validationUtil, MappingUtil mappingUtil) {
        this.operationRepository = operationRepository;
        this.operationMapper = operationMapper;
        this.validationUtil = validationUtil;
        this.mappingUtil = mappingUtil;
    }

    @Override
    public OperationDto get(Long operationId, Long accountId) {
        Operation operation = getOperation(operationId, accountId);
        return operationMapper.mapDto(operation);
    }

    @Override
    public List<OperationDto> getAllByAccount(Long accountId) {
        List<Operation> operations = operationRepository.getAllByAccount(accountId);
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
    public List<OperationDto> getAllLendByAccount(Long accountId) {
        List<Operation> operations = operationRepository.getAllLendByAccount(accountId);
        return operationMapper.mapDtos(operations);
    }

    @Override
    public List<OperationDto> getAllLoan() {
        return operationMapper.mapDtos(operationRepository.getAllLoan());
    }

    @Override
    public List<OperationDto> getAllLoanByAccount(Long accountId) {
        List<Operation> operations = operationRepository.getAllLoanByAccount(accountId);
        return operationMapper.mapDtos(operations);
    }

    @Override
    public List<OperationDto> getBetweenDates(LocalDate startDate, LocalDate endDate) {
        return operationMapper.mapDtos(operationRepository.getBetweenDates(startDate, endDate));
    }

    @Override
    public List<OperationDto> getBetweenDatesByAccount(LocalDate startDate, LocalDate endDate,
                                                       Long accountId) {
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
                                                              Long accountId) {
        List<Operation> operations = operationRepository.getAllLendBetweenDatesByAccount(startDate, endDate, accountId);
        return operationMapper.mapDtos(operations);
    }

    @Override
    public List<OperationDto> getAllLoanBetweenDatesByAccount(LocalDate startDate, LocalDate endDate,
                                                              Long accountId) {
        List<Operation> operations = operationRepository.getAllLoanBetweenDatesByAccount(startDate, endDate, accountId);
        return operationMapper.mapDtos(operations);
    }

    @Transactional
    @Override
    public OperationDto update(OperationDto operationDto, Long accountId) {
        Operation operation = getOperation(mappingUtil.mapId(operationDto), accountId);
        operationMapper.update(operationDto, operation);
        return operationMapper.mapDto(operationRepository.save(operation, accountId));
    }

    @Transactional
    @Override
    public OperationDto create(OperationDto operationDto, Long accountId) {
        validationUtil.checkIsNew(operationDto);
        Operation operation = operationMapper.map(operationDto);
        operation = operationRepository.save(operation, accountId);
        return operationMapper.mapDto(operation);
    }

    @Override
    public void delete(Long operationId, Long accountId) {
        int result = operationRepository.delete(operationId, accountId);
        validationUtil.checkNotFoundWithId(result != 0, operationId);
    }

    @Override
    public void deleteAllByAccount(Long accountId) {
        validationUtil.checkNotFound(operationRepository.deleteAllByAccount(accountId) != 0);
    }

    private Operation getOperation(Long operationId, Long personId) {
        return getEntityFromOptional(operationRepository.get(operationId, personId), operationId);
    }
}
