package ru.smaginv.debtmanager.service.operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.smaginv.debtmanager.entity.account.Account;
import ru.smaginv.debtmanager.entity.account.AccountStatus;
import ru.smaginv.debtmanager.entity.account.AccountType;
import ru.smaginv.debtmanager.entity.operation.Operation;
import ru.smaginv.debtmanager.entity.operation.OperationType;
import ru.smaginv.debtmanager.repository.account.AccountRepository;
import ru.smaginv.debtmanager.repository.operation.OperationRepository;
import ru.smaginv.debtmanager.util.AppUtil;
import ru.smaginv.debtmanager.util.MappingUtil;
import ru.smaginv.debtmanager.util.exception.AccountOperationException;
import ru.smaginv.debtmanager.util.exception.EntityStatusException;
import ru.smaginv.debtmanager.util.validation.ValidationUtil;
import ru.smaginv.debtmanager.web.dto.account.AccountDto;
import ru.smaginv.debtmanager.web.dto.account.AccountIdDto;
import ru.smaginv.debtmanager.web.dto.operation.*;
import ru.smaginv.debtmanager.web.mapping.AccountMapper;
import ru.smaginv.debtmanager.web.mapping.OperationMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

import static ru.smaginv.debtmanager.util.entity.EntityUtil.getEntityFromOptional;

@Service
@Transactional(readOnly = true)
public class OperationServiceImpl implements OperationService {

    private final OperationRepository operationRepository;
    private final AccountRepository accountRepository;
    private final OperationMapper operationMapper;
    private final AccountMapper accountMapper;
    private final ValidationUtil validationUtil;
    private final MappingUtil mappingUtil;

    @Autowired
    public OperationServiceImpl(OperationRepository operationRepository, AccountRepository accountRepository,
                                OperationMapper operationMapper, AccountMapper accountMapper,
                                ValidationUtil validationUtil, MappingUtil mappingUtil) {
        this.operationRepository = operationRepository;
        this.accountRepository = accountRepository;
        this.operationMapper = operationMapper;
        this.accountMapper = accountMapper;
        this.validationUtil = validationUtil;
        this.mappingUtil = mappingUtil;
    }

    @Override
    public OperationDto get(OperationIdDto operationIdDto) {
        Operation operation = getOperation(mappingUtil.mapId(operationIdDto));
        return operationMapper.mapDto(operation);
    }

    @Override
    public List<OperationDto> getAllByAccount(AccountIdDto accountIdDto) {
        Long accountId = mappingUtil.mapId(accountIdDto.getId());
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
    public void update(OperationUpdateDto operationUpdateDto) {
        Operation operation = getOperation(mappingUtil.mapId(operationUpdateDto));
        Account account = getAccount(mappingUtil.mapId(operationUpdateDto.getAccountId()));
        LocalDateTime newOperDate = mappingUtil.parseStringToLocalDateTime(operationUpdateDto.getOperDate());
        if (Objects.nonNull(newOperDate) && account.getOpenDate().isAfter(newOperDate))
            throw new AccountOperationException("operation date must be: " + account.getOpenDate() + " or later");
        rollBackOperation(account, operation);
        operationMapper.update(operationUpdateDto, operation);
        operationRepository.update(operation);
        setAccountAmount(account, operation);
        accountRepository.save(account);
    }

    @Transactional
    @Override
    public OperationDto create(OperationDto operationDto) {
        Account account = getAccount(mappingUtil.mapId(operationDto.getAccountId()));
        Operation operation = operationMapper.map(operationDto);
        setAccountAmount(account, operation);
        accountRepository.save(account);
        operation.setOperDate(LocalDateTime.now());
        operation.setAccount(account);
        operation = operationRepository.create(operation);
        return operationMapper.mapDto(operation);
    }

    @Transactional
    @Override
    public AccountDto closeAccount(OperationDto operationDto) {
        Account account = getAccount(mappingUtil.mapId(operationDto.getAccountId()));
        AccountType accountType = account.getAccountType();
        Operation operation = operationMapper.map(operationDto);
        OperationType operationType = operation.getOperationType();
        if (accountType.equals(AccountType.LEND) && operationType.equals(OperationType.EXPENSE))
            throw new AccountOperationException("operation type must be: " + OperationType.RECEIPT);
        else if (accountType.equals(AccountType.LOAN) && operationType.equals(OperationType.RECEIPT))
            throw new AccountOperationException("operation type must be: " + OperationType.EXPENSE);
        if (account.getAmount().compareTo(operation.getAmount()) != 0)
            throw new AccountOperationException("amount of operation must be: " + account.getAmount());
        setAccountAmount(account, operation);
        operation.setOperDate(LocalDateTime.now());
        operationRepository.create(operation);
        accountRepository.save(account);
        return accountMapper.mapDto(account);
    }

    @Transactional
    @Override
    public void delete(OperationIdsDto operationIdsDto) {
        Account account = getAccount(mappingUtil.mapId(operationIdsDto.getAccountId()));
        Long operationId = mappingUtil.mapId(operationIdsDto);
        Operation operation = getOperation(operationId);
        rollBackOperation(account, operation);
        accountRepository.save(account);
        int result = operationRepository.delete(operationId);
        validationUtil.checkNotFoundWithId(result != 0, operationId);
    }

    @Transactional
    @Override
    public void deleteAllByAccount(AccountIdDto accountIdDto) {
        Long accountId = mappingUtil.mapId(accountIdDto);
        Account account = getAccount(accountId);
        List<Operation> operations = operationRepository.getAllByAccount(accountId);
        operations.forEach(operation -> rollBackOperation(account, operation));
        accountRepository.save(account);
        validationUtil.checkNotFound(operationRepository.deleteAllByAccount(accountId) != 0);
    }

    private void setAccountAmount(Account account, Operation operation) {
        BigDecimal amount;
        if (account.getAccountType().equals(AccountType.LEND)) {
            if (operation.getOperationType().equals(OperationType.RECEIPT))
                amount = account.getAmount().subtract(operation.getAmount());
            else
                amount = account.getAmount().add(operation.getAmount());
        } else {
            if (operation.getOperationType().equals(OperationType.RECEIPT))
                amount = account.getAmount().add(operation.getAmount());
            else
                amount = account.getAmount().subtract(operation.getAmount());
        }
        if (amount.compareTo(BigDecimal.ZERO) == 0) {
            account.setAccountStatus(AccountStatus.INACTIVE);
            account.setClosedDate(LocalDateTime.now());
        }
        account.setAmount(amount);
    }

    private void rollBackOperation(Account account, Operation operation) {
        BigDecimal amount;
        if (account.getAccountType().equals(AccountType.LEND)) {
            if (operation.getOperationType().equals(OperationType.RECEIPT))
                amount = account.getAmount().add(operation.getAmount());
            else
                amount = account.getAmount().subtract(operation.getAmount());
        } else {
            if (operation.getOperationType().equals(OperationType.RECEIPT))
                amount = account.getAmount().subtract(operation.getAmount());
            else
                amount = account.getAmount().add(operation.getAmount());
        }
        if (account.getAccountStatus().equals(AccountStatus.INACTIVE) && amount.compareTo(BigDecimal.ZERO) != 0) {
            account.setAccountStatus(AccountStatus.ACTIVE);
            account.setClosedDate(null);
        }
        account.setAmount(amount);
    }

    private Account getAccount(Long accountId) {
        Account account = getEntityFromOptional(accountRepository.get(accountId), accountId);
        if (account.getAccountStatus().equals(AccountStatus.INACTIVE))
            throw new EntityStatusException("account status must be 'ACTIVE'");
        return account;
    }

    private Operation getOperation(Long operationId) {
        return getEntityFromOptional(operationRepository.get(operationId), operationId);
    }
}
