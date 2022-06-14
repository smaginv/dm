package ru.smaginv.debtmanager.dm.service.operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.smaginv.debtmanager.dm.entity.account.Account;
import ru.smaginv.debtmanager.dm.entity.account.AccountStatus;
import ru.smaginv.debtmanager.dm.entity.account.AccountType;
import ru.smaginv.debtmanager.dm.entity.operation.Operation;
import ru.smaginv.debtmanager.dm.entity.operation.OperationType;
import ru.smaginv.debtmanager.dm.repository.account.AccountRepository;
import ru.smaginv.debtmanager.dm.repository.operation.OperationRepository;
import ru.smaginv.debtmanager.dm.util.AppUtil;
import ru.smaginv.debtmanager.dm.util.MappingUtil;
import ru.smaginv.debtmanager.dm.util.exception.AccountOperationException;
import ru.smaginv.debtmanager.dm.util.exception.EntityStatusException;
import ru.smaginv.debtmanager.dm.util.validation.ValidationUtil;
import ru.smaginv.debtmanager.dm.web.dto.account.AccountDto;
import ru.smaginv.debtmanager.dm.web.dto.account.AccountIdDto;
import ru.smaginv.debtmanager.dm.web.dto.operation.*;
import ru.smaginv.debtmanager.dm.web.mapping.AccountMapper;
import ru.smaginv.debtmanager.dm.web.mapping.OperationMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

import static ru.smaginv.debtmanager.dm.util.entity.EntityUtil.getEntityFromOptional;

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
    public OperationDto get(Long userId, OperationIdDto operationIdDto) {
        Operation operation = getOperation(userId, mappingUtil.mapId(operationIdDto));
        return operationMapper.mapDto(operation);
    }

    @Override
    public List<OperationDto> getAllByAccount(Long userId, AccountIdDto accountIdDto) {
        Long accountId = mappingUtil.mapId(accountIdDto.getId());
        List<Operation> operations = operationRepository.getAllByAccount(userId, accountId);
        return operationMapper.mapDtos(operations);
    }

    @Override
    public List<OperationDto> getAll(Long userId) {
        return operationMapper.mapDtos(operationRepository.getAll(userId));
    }

    @Override
    public List<OperationDto> getByType(Long userId, OperationTypeDto operationTypeDto) {
        OperationType operationType = OperationType.getByValue(operationTypeDto.getType());
        Long accountId = operationTypeDto.getAccountId();
        return operationMapper.mapDtos(operationRepository.getByType(userId, accountId, operationType));
    }

    @Override
    public List<OperationDto> find(Long userId, OperationSearchDto operationSearchDto) {
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
        List<Operation> operations = operationRepository.find(
                userId, accountId, operationType, startDateTime, endDateTime
        );
        return operationMapper.mapDtos(operations);
    }

    @Caching(evict = {
            @CacheEvict(value = "accounts", key = "#userId + '_all'"),
            @CacheEvict(value = "accounts", key = "#userId + '_ACTIVE'"),
            @CacheEvict(value = "accounts", key = "#userId + '_LEND'"),
            @CacheEvict(value = "accounts", key = "#userId + '_LOAN'")
    })
    @Transactional
    @Override
    public void update(Long userId, OperationUpdateDto operationUpdateDto) {
        Operation operation = getOperation(userId, mappingUtil.mapId(operationUpdateDto));
        Account account = getAccount(userId, mappingUtil.mapId(operationUpdateDto.getAccountId()));
        LocalDateTime newOperDate = mappingUtil.parseStringToLocalDateTime(operationUpdateDto.getOperDate());
        if (Objects.nonNull(newOperDate) && account.getOpenDate().isAfter(newOperDate))
            throw new AccountOperationException("operation date must be: " + account.getOpenDate() + " or later");
        rollBackOperation(account, operation);
        operationMapper.update(operationUpdateDto, operation);
        operationRepository.update(operation);
        setAccountAmount(account, operation);
        accountRepository.save(account);
    }

    @Caching(evict = {
            @CacheEvict(value = "accounts", key = "#userId + '_all'"),
            @CacheEvict(value = "accounts", key = "#userId + '_ACTIVE'"),
            @CacheEvict(value = "accounts", key = "#userId + '_LEND'"),
            @CacheEvict(value = "accounts", key = "#userId + '_LOAN'")
    })
    @Transactional
    @Override
    public OperationDto create(Long userId, OperationDto operationDto) {
        Account account = getAccount(userId, mappingUtil.mapId(operationDto.getAccountId()));
        Operation operation = operationMapper.map(operationDto);
        setAccountAmount(account, operation);
        accountRepository.save(account);
        operation.setOperDate(LocalDateTime.now());
        operation.setAccount(account);
        operation = operationRepository.create(operation);
        return operationMapper.mapDto(operation);
    }

    @Caching(evict = {
            @CacheEvict(value = "accounts", key = "#userId + '_all'"),
            @CacheEvict(value = "accounts", key = "#userId + '_ACTIVE'"),
            @CacheEvict(value = "accounts", key = "#userId + '_INACTIVE'"),
            @CacheEvict(value = "accounts", key = "#userId + '_LEND'"),
            @CacheEvict(value = "accounts", key = "#userId + '_LOAN'")
    })
    @Transactional
    @Override
    public AccountDto closeAccount(Long userId, OperationDto operationDto) {
        Account account = getAccount(userId, mappingUtil.mapId(operationDto.getAccountId()));
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
        operation.setAccount(account);
        operationRepository.create(operation);
        accountRepository.save(account);
        return accountMapper.mapDto(account);
    }

    @Caching(evict = {
            @CacheEvict(value = "accounts", key = "#userId + '_all'"),
            @CacheEvict(value = "accounts", key = "#userId + '_ACTIVE'"),
            @CacheEvict(value = "accounts", key = "#userId + '_LEND'"),
            @CacheEvict(value = "accounts", key = "#userId + '_LOAN'")
    })
    @Transactional
    @Override
    public void delete(Long userId, OperationIdsDto operationIdsDto) {
        Account account = getAccount(userId, mappingUtil.mapId(operationIdsDto.getAccountId()));
        Long operationId = mappingUtil.mapId(operationIdsDto);
        Operation operation = getOperation(userId, operationId);
        rollBackOperation(account, operation);
        accountRepository.save(account);
        int result = operationRepository.delete(userId, operationId);
        validationUtil.checkNotFoundWithId(result != 0, operationId);
    }

    @Caching(evict = {
            @CacheEvict(value = "accounts", key = "#userId + '_all'"),
            @CacheEvict(value = "accounts", key = "#userId + '_ACTIVE'"),
            @CacheEvict(value = "accounts", key = "#userId + '_LEND'"),
            @CacheEvict(value = "accounts", key = "#userId + '_LOAN'")
    })
    @Transactional
    @Override
    public void deleteAllByAccount(Long userId, AccountIdDto accountIdDto) {
        Long accountId = mappingUtil.mapId(accountIdDto);
        Account account = getAccount(userId, accountId);
        List<Operation> operations = operationRepository.getAllByAccount(userId, accountId);
        operations.forEach(operation -> rollBackOperation(account, operation));
        accountRepository.save(account);
        validationUtil.checkNotFound(operationRepository.deleteAllByAccount(userId, accountId) != 0);
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

    private Account getAccount(Long userId, Long accountId) {
        Account account = getEntityFromOptional(accountRepository.get(userId, accountId), accountId);
        if (account.getAccountStatus().equals(AccountStatus.INACTIVE))
            throw new EntityStatusException("account status must be 'ACTIVE'");
        return account;
    }

    private Operation getOperation(Long userId, Long operationId) {
        return getEntityFromOptional(operationRepository.get(userId, operationId), operationId);
    }
}
