package ru.smaginv.debtmanager.service.operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.smaginv.debtmanager.entity.operation.Operation;
import ru.smaginv.debtmanager.entity.operation.OperationType;
import ru.smaginv.debtmanager.repository.operation.OperationRepository;
import ru.smaginv.debtmanager.util.AppUtil;
import ru.smaginv.debtmanager.util.MappingUtil;
import ru.smaginv.debtmanager.util.validation.ValidationUtil;
import ru.smaginv.debtmanager.web.dto.operation.OperationDto;
import ru.smaginv.debtmanager.web.dto.operation.OperationSearchDto;
import ru.smaginv.debtmanager.web.dto.operation.OperationTypeDto;
import ru.smaginv.debtmanager.web.mapping.OperationMapper;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

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
    public OperationDto get(Long accountId, Long operationId) {
        Operation operation = getOperation(accountId, operationId);
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
    public List<OperationDto> getByType(OperationTypeDto operationTypeDto) {
        OperationType operationType = OperationType.getByValue(operationTypeDto.getType());
        Long accountId = operationTypeDto.getAccountId();
        return operationMapper.mapDtos(operationRepository.getByType(accountId, operationType));
    }

    @Override
    public List<OperationDto> find(OperationSearchDto operationSearchDto) {
        Long accountId = operationSearchDto.getAccountId();
        OperationType operationType = OperationType.getByValue(operationSearchDto.getType());
        LocalDateTime startDateTime;
        if (Objects.nonNull(operationSearchDto.getStartDate()))
            startDateTime = operationSearchDto.getStartDate().atStartOfDay();
        else
            startDateTime = AppUtil.MIN_DATE;
        LocalDateTime endDateTime;
        if (Objects.nonNull(operationSearchDto.getEndDate()))
            endDateTime = operationSearchDto.getEndDate().atTime(LocalTime.MAX);
        else
            endDateTime = LocalDateTime.now();
        List<Operation> operations = operationRepository.find(accountId, operationType, startDateTime, endDateTime);
        return operationMapper.mapDtos(operations);
    }

    @Transactional
    @Override
    public void update(Long accountId, OperationDto operationDto) {
        Operation operation = getOperation(accountId, mappingUtil.mapId(operationDto));
        if (!operation.getOperationType().name().equals(operationDto.getType()))
            operationDto.setType(null);
        operationMapper.update(operationDto, operation);
        operationRepository.save(accountId, operation);
    }

    @Transactional
    @Override
    public OperationDto create(Long accountId, OperationDto operationDto) {
        Operation operation = operationMapper.map(operationDto);
        if (Objects.isNull(operation.getOperDate()))
            operation.setOperDate(LocalDateTime.now());
        operation = operationRepository.save(accountId, operation);
        return operationMapper.mapDto(operation);
    }

    @Transactional
    @Override
    public void delete(Long accountId, Long operationId) {
        int result = operationRepository.delete(accountId, operationId);
        validationUtil.checkNotFoundWithId(result != 0, operationId);
    }

    @Transactional
    @Override
    public void deleteAllByAccount(Long accountId) {
        validationUtil.checkNotFound(operationRepository.deleteAllByAccount(accountId) != 0);
    }

    private Operation getOperation(Long accountId, Long operationId) {
        return getEntityFromOptional(operationRepository.get(accountId, operationId), operationId);
    }
}
