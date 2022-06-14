package ru.smaginv.debtmanager.dm.service.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.smaginv.debtmanager.dm.entity.account.Account;
import ru.smaginv.debtmanager.dm.entity.account.AccountStatus;
import ru.smaginv.debtmanager.dm.entity.account.AccountType;
import ru.smaginv.debtmanager.dm.entity.operation.Operation;
import ru.smaginv.debtmanager.dm.repository.account.AccountRepository;
import ru.smaginv.debtmanager.dm.repository.operation.OperationRepository;
import ru.smaginv.debtmanager.dm.util.MappingUtil;
import ru.smaginv.debtmanager.dm.util.exception.EntityStatusException;
import ru.smaginv.debtmanager.dm.util.validation.ValidationUtil;
import ru.smaginv.debtmanager.dm.web.dto.account.*;
import ru.smaginv.debtmanager.dm.web.dto.person.PersonIdDto;
import ru.smaginv.debtmanager.dm.web.mapping.AccountMapper;
import ru.smaginv.debtmanager.dm.web.mapping.OperationMapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static ru.smaginv.debtmanager.dm.util.entity.EntityUtil.getEntityFromOptional;

@Service
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final OperationRepository operationRepository;
    private final AccountMapper accountMapper;
    private final OperationMapper operationMapper;
    private final ValidationUtil validationUtil;
    private final MappingUtil mappingUtil;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, OperationRepository operationRepository,
                              AccountMapper accountMapper, OperationMapper operationMapper,
                              ValidationUtil validationUtil, MappingUtil mappingUtil) {
        this.accountRepository = accountRepository;
        this.operationRepository = operationRepository;
        this.accountMapper = accountMapper;
        this.operationMapper = operationMapper;
        this.validationUtil = validationUtil;
        this.mappingUtil = mappingUtil;
    }

    @Override
    public AccountDto get(Long userId, AccountIdDto accountIdDto) {
        Account account = getAccount(userId, mappingUtil.mapId(accountIdDto));
        return accountMapper.mapDto(account);
    }

    @Override
    public AccountInfoDto getWithOperations(Long userId, AccountIdDto accountIdDto) {
        Long accountId = mappingUtil.mapId(accountIdDto);
        Account account = getAccount(userId, accountId);
        List<Operation> operations = operationRepository.getAllByAccount(userId, accountId);
        AccountInfoDto accountInfoDto = accountMapper.mapInfoDto(account);
        accountInfoDto.setOperations(operationMapper.mapDtos(operations));
        return accountInfoDto;
    }

    @Cacheable(
            value = "accounts",
            key = "#userId + '_all'"
    )
    @Override
    public List<AccountDto> getAll(Long userId) {
        return accountMapper.mapDtos(accountRepository.getAll(userId));
    }

    @Override
    public List<AccountDto> getAllByPerson(Long userId, PersonIdDto personIdDto) {
        List<Account> accounts = accountRepository.getAllByPerson(userId, mappingUtil.mapId(personIdDto));
        return accountMapper.mapDtos(accounts);
    }

    @Cacheable(
            value = "accounts",
            key = "#userId + '_' + #accountStatusDto.status"
    )
    @Override
    public List<AccountDto> getAllByStatus(Long userId, AccountStatusDto accountStatusDto) {
        AccountStatus accountStatus = AccountStatus.getByValue(accountStatusDto.getStatus());
        List<Account> accounts = accountRepository.getAllByStatus(userId, accountStatus);
        return accountMapper.mapDtos(accounts);
    }

    @Cacheable(
            value = "accounts",
            key = "#userId + '_' + #accountTypeDto.type"
    )
    @Override
    public List<AccountDto> getAllByType(Long userId, AccountTypeDto accountTypeDto) {
        AccountType accountType = AccountType.getByValue(accountTypeDto.getType());
        List<Account> accounts = accountRepository.getAllByType(userId, accountType);
        return accountMapper.mapDtos(accounts);
    }

    @Override
    public String getActiveAccountsTotalAmountByType(Long userId, AccountTypeDto accountTypeDto) {
        AccountType accountType = AccountType.getByValue(accountTypeDto.getType());
        BigDecimal amount = accountRepository.getActiveAccountsTotalAmountByType(userId, accountType);
        return checkAmount(amount);
    }

    @Override
    public String getInactiveAccountsTotalAmountByType(Long userId, AccountTypeDto accountTypeDto) {
        AccountType accountType = AccountType.getByValue(accountTypeDto.getType());
        BigDecimal amount = accountRepository.getInactiveAccountsTotalAmountByType(userId, accountType);
        return checkAmount(amount);
    }

    @Caching(evict = {
            @CacheEvict(value = "accounts", key = "#userId + '_all'"),
            @CacheEvict(value = "accounts", key = "#userId + '_ACTIVE'"),
            @CacheEvict(value = "accounts", key = "#userId + '_LEND'"),
            @CacheEvict(value = "accounts", key = "#userId + '_LOAN'")
    })
    @Transactional
    @Override
    public void update(Long userId, AccountUpdateDto accountUpdateDto) {
        Account account = getAccount(userId, mappingUtil.mapId(accountUpdateDto));
        accountMapper.update(accountUpdateDto, account);
        accountRepository.update(account);
    }

    @Caching(evict = {
            @CacheEvict(value = "accounts", key = "#userId + '_all'"),
            @CacheEvict(value = "accounts", key = "#userId + '_ACTIVE'"),
            @CacheEvict(value = "accounts", key = "#userId + '_LEND'"),
            @CacheEvict(value = "accounts", key = "#userId + '_LOAN'")
    })
    @Transactional
    @Override
    public AccountDto create(Long userId, AccountDto accountDto) {
        Account account = accountMapper.map(accountDto);
        account.setOpenDate(LocalDateTime.now());
        account.setAccountStatus(AccountStatus.ACTIVE);
        account = accountRepository.create(userId, mappingUtil.mapId(accountDto.getPersonId()), account);
        return accountMapper.mapDto(account);
    }

    @Caching(evict = {
            @CacheEvict(value = "accounts", key = "#userId + '_all'"),
            @CacheEvict(value = "accounts", key = "#userId + '_INACTIVE'"),
            @CacheEvict(value = "accounts", key = "#userId + '_LEND'"),
            @CacheEvict(value = "accounts", key = "#userId + '_LOAN'")
    })
    @Transactional
    @Override
    public void delete(Long userId, AccountIdDto accountIdDto) {
        Long accountId = mappingUtil.mapId(accountIdDto);
        Account account = getAccount(userId, accountId);
        if (account.getAccountStatus().equals(AccountStatus.ACTIVE))
            throw new EntityStatusException("status of account must be 'INACTIVE'");
        int result = accountRepository.delete(userId, accountId);
        validationUtil.checkNotFoundWithId(result != 0, accountId);
    }

    @Caching(evict = {
            @CacheEvict(value = "accounts", key = "#userId + '_all'"),
            @CacheEvict(value = "accounts", key = "#userId + '_INACTIVE'"),
            @CacheEvict(value = "accounts", key = "#userId + '_LEND'"),
            @CacheEvict(value = "accounts", key = "#userId + '_LOAN'")
    })
    @Transactional
    @Override
    public void deleteAllInactiveByPerson(Long userId, PersonIdDto personIdDto) {
        int result = accountRepository.deleteAllInactiveByPerson(userId, mappingUtil.mapId(personIdDto));
        validationUtil.checkNotFound(result != 0);
    }

    @Caching(evict = {
            @CacheEvict(value = "accounts", key = "#userId + '_all'"),
            @CacheEvict(value = "accounts", key = "#userId + '_INACTIVE'"),
            @CacheEvict(value = "accounts", key = "#userId + '_LEND'"),
            @CacheEvict(value = "accounts", key = "#userId + '_LOAN'")
    })
    @Transactional
    @Override
    public void deleteAllInactive(Long userId) {
        int result = accountRepository.deleteAllInactive(userId);
        validationUtil.checkNotFound(result != 0);
    }

    private Account getAccount(Long userId, Long accountId) {
        return getEntityFromOptional(accountRepository.get(userId, accountId), accountId);
    }

    private String checkAmount(BigDecimal amount) {
        if (Objects.isNull(amount))
            amount = BigDecimal.ZERO;
        return String.valueOf(amount.setScale(2, RoundingMode.DOWN));
    }
}
