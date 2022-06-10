package ru.smaginv.debtmanager.service.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.smaginv.debtmanager.entity.account.Account;
import ru.smaginv.debtmanager.entity.account.AccountStatus;
import ru.smaginv.debtmanager.entity.account.AccountType;
import ru.smaginv.debtmanager.entity.operation.Operation;
import ru.smaginv.debtmanager.repository.account.AccountRepository;
import ru.smaginv.debtmanager.repository.operation.OperationRepository;
import ru.smaginv.debtmanager.util.MappingUtil;
import ru.smaginv.debtmanager.util.exception.EntityStatusException;
import ru.smaginv.debtmanager.util.validation.ValidationUtil;
import ru.smaginv.debtmanager.web.dto.account.*;
import ru.smaginv.debtmanager.web.dto.person.PersonIdDto;
import ru.smaginv.debtmanager.web.mapping.AccountMapper;
import ru.smaginv.debtmanager.web.mapping.OperationMapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static ru.smaginv.debtmanager.util.entity.EntityUtil.getEntityFromOptional;

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
    public AccountDto get(AccountIdDto accountIdDto) {
        Account account = getAccount(mappingUtil.mapId(accountIdDto));
        return accountMapper.mapDto(account);
    }

    @Override
    public AccountInfoDto getWithOperations(AccountIdDto accountIdDto) {
        Long accountId = mappingUtil.mapId(accountIdDto);
        Account account = getAccount(accountId);
        List<Operation> operations = operationRepository.getAllByAccount(accountId);
        AccountInfoDto accountInfoDto = accountMapper.mapInfoDto(account);
        accountInfoDto.setOperations(operationMapper.mapDtos(operations));
        return accountInfoDto;
    }

    @Override
    public List<AccountDto> getAll() {
        return accountMapper.mapDtos(accountRepository.getAll());
    }

    @Override
    public List<AccountDto> getAllByPerson(PersonIdDto personIdDto) {
        List<Account> accounts = accountRepository.getAllByPerson(mappingUtil.mapId(personIdDto));
        return accountMapper.mapDtos(accounts);
    }

    @Override
    public List<AccountDto> getAllByStatus(AccountStatusDto accountStatusDto) {
        AccountStatus accountStatus = AccountStatus.getByValue(accountStatusDto.getStatus());
        List<Account> accounts = accountRepository.getAllByStatus(accountStatus);
        return accountMapper.mapDtos(accounts);
    }

    @Override
    public List<AccountDto> getAllByType(AccountTypeDto accountTypeDto) {
        AccountType accountType = AccountType.getByValue(accountTypeDto.getType());
        List<Account> accounts = accountRepository.getAllByType(accountType);
        return accountMapper.mapDtos(accounts);
    }

    @Override
    public String getActiveAccountsTotalAmountByType(AccountTypeDto accountTypeDto) {
        AccountType accountType = AccountType.getByValue(accountTypeDto.getType());
        BigDecimal amount = accountRepository.getActiveAccountsTotalAmountByType(accountType);
        return checkAmount(amount);
    }

    @Override
    public String getInactiveAccountsTotalAmountByType(AccountTypeDto accountTypeDto) {
        AccountType accountType = AccountType.getByValue(accountTypeDto.getType());
        BigDecimal amount = accountRepository.getInactiveAccountsTotalAmountByType(accountType);
        return checkAmount(amount);
    }

    @Transactional
    @Override
    public void update(AccountUpdateDto accountUpdateDto) {
        Account account = getAccount(mappingUtil.mapId(accountUpdateDto));
        accountMapper.update(accountUpdateDto, account);
        accountRepository.update(account);
    }

    @Transactional
    @Override
    public AccountDto create(AccountDto accountDto) {
        Account account = accountMapper.map(accountDto);
        account.setOpenDate(LocalDateTime.now());
        account.setAccountStatus(AccountStatus.ACTIVE);
        account = accountRepository.create(mappingUtil.mapId(accountDto.getPersonId()), account);
        return accountMapper.mapDto(account);
    }

    @Transactional
    @Override
    public void delete(AccountIdDto accountIdDto) {
        Long accountId = mappingUtil.mapId(accountIdDto);
        Account account = getAccount(accountId);
        if (account.getAccountStatus().equals(AccountStatus.ACTIVE))
            throw new EntityStatusException("status of account must be 'INACTIVE'");
        int result = accountRepository.delete(accountId);
        validationUtil.checkNotFoundWithId(result != 0, accountId);
    }

    @Transactional
    @Override
    public void deleteAllInactiveByPerson(PersonIdDto personIdDto) {
        int result = accountRepository.deleteAllInactiveByPerson(mappingUtil.mapId(personIdDto));
        validationUtil.checkNotFound(result != 0);
    }

    @Transactional
    @Override
    public void deleteAllInactive() {
        int result = accountRepository.deleteAllInactive();
        validationUtil.checkNotFound(result != 0);
    }

    private Account getAccount(Long accountId) {
        return getEntityFromOptional(accountRepository.get(accountId), accountId);
    }

    private String checkAmount(BigDecimal amount) {
        if (Objects.isNull(amount))
            amount = BigDecimal.ZERO;
        return String.valueOf(amount.setScale(2, RoundingMode.DOWN));
    }
}
