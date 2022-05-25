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
import ru.smaginv.debtmanager.util.exception.AccountActiveException;
import ru.smaginv.debtmanager.util.exception.AccountOperationException;
import ru.smaginv.debtmanager.util.validation.ValidationUtil;
import ru.smaginv.debtmanager.web.dto.account.AccountDto;
import ru.smaginv.debtmanager.web.dto.operation.OperationDto;
import ru.smaginv.debtmanager.web.dto.operation.OperationSearchDto;
import ru.smaginv.debtmanager.web.dto.operation.OperationTypeDto;
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
        Account account = getAccount(accountId);
        Operation operation = getOperation(accountId, mappingUtil.mapId(operationDto));
        rollBackOperation(account, operation);
        if (!operation.getOperationType().name().equals(operationDto.getType()))
            operationDto.setType(null);
        operationMapper.update(operationDto, operation);
        operationRepository.save(accountId, operation);
        setAccountAmount(account, operation);
        accountRepository.save(account);
    }

    @Transactional
    @Override
    public OperationDto create(Long accountId, OperationDto operationDto) {
        Account account = getAccount(accountId);
        Operation operation = operationMapper.map(operationDto);
        setAccountAmount(account, operation);
        accountRepository.save(account);
        if (Objects.isNull(operation.getOperDate()))
            operation.setOperDate(LocalDateTime.now());
        operation = operationRepository.save(accountId, operation);
        return operationMapper.mapDto(operation);
    }

    @Transactional
    @Override
    public AccountDto closeAccount(Long accountId, OperationDto operationDto) {
        Account account = getAccount(accountId);
        AccountType accountType = account.getAccountType();
        Operation operation = operationMapper.map(operationDto);
        OperationType operationType = operation.getOperationType();
        if (accountType.equals(AccountType.LEND) && operationType.equals(OperationType.EXPENSE))
            throw new AccountOperationException("operation type must be 'RECEIPT'");
        else if (accountType.equals(AccountType.LOAN) && operationType.equals(OperationType.RECEIPT))
            throw new AccountOperationException("operation type must be 'EXPENSE'");
        if (account.getAmount().compareTo(operation.getAmount()) != 0)
            throw new AccountOperationException("amount of operation must be: " + account.getAmount());
        setAccountAmount(account, operation);
        if (Objects.isNull(operation.getOperDate()))
            operation.setOperDate(LocalDateTime.now());
        operationRepository.save(accountId, operation);
        accountRepository.save(account);
        return accountMapper.mapDto(account);
    }

    @Transactional
    @Override
    public void delete(Long accountId, Long operationId) {
        Account account = getAccount(accountId);
        Operation operation = getOperation(accountId, operationId);
        rollBackOperation(account, operation);
        accountRepository.save(account);
        int result = operationRepository.delete(accountId, operationId);
        validationUtil.checkNotFoundWithId(result != 0, operationId);
    }

    @Transactional
    @Override
    public void deleteAllByAccount(Long accountId) {
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
        Account account = getEntityFromOptional(accountRepository.getById(accountId), accountId);
        if (account.getAccountStatus().equals(AccountStatus.INACTIVE))
            throw new AccountActiveException("account status must be 'ACTIVE' or 'RESUMED'");
        return account;
    }

    private Operation getOperation(Long accountId, Long operationId) {
        return getEntityFromOptional(operationRepository.get(accountId, operationId), operationId);
    }
}
