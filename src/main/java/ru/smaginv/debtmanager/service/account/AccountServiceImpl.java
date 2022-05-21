package ru.smaginv.debtmanager.service.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.smaginv.debtmanager.entity.account.Account;
import ru.smaginv.debtmanager.entity.account.AccountType;
import ru.smaginv.debtmanager.repository.account.AccountRepository;
import ru.smaginv.debtmanager.service.operation.OperationService;
import ru.smaginv.debtmanager.util.MappingUtil;
import ru.smaginv.debtmanager.util.validation.ValidationUtil;
import ru.smaginv.debtmanager.web.dto.account.AccountDto;
import ru.smaginv.debtmanager.web.dto.account.AccountInfoDto;
import ru.smaginv.debtmanager.web.dto.account.AccountStateDto;
import ru.smaginv.debtmanager.web.dto.account.AccountTypeDto;
import ru.smaginv.debtmanager.web.dto.operation.OperationDto;
import ru.smaginv.debtmanager.web.mapping.AccountMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static ru.smaginv.debtmanager.util.entity.EntityUtil.getEntityFromOptional;

@Service
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final OperationService operationService;
    private final ValidationUtil validationUtil;
    private final MappingUtil mappingUtil;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, AccountMapper accountMapper,
                              OperationService operationService, ValidationUtil validationUtil,
                              MappingUtil mappingUtil) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.operationService = operationService;
        this.validationUtil = validationUtil;
        this.mappingUtil = mappingUtil;
    }

    @Override
    public AccountDto get(Long personId, Long accountId) {
        Account account = getAccount(personId, accountId);
        return accountMapper.mapDto(account);
    }

    @Override
    public AccountInfoDto getWithOperations(Long personId, Long accountId) {
        Account account = getAccount(personId, accountId);
        List<OperationDto> operations = operationService.getAllByAccount(accountId);
        AccountInfoDto accountInfoDto = accountMapper.mapInfoDto(account);
        accountInfoDto.setOperations(operations);
        return accountInfoDto;
    }

    @Override
    public List<AccountDto> getAll() {
        return accountMapper.mapDtos(accountRepository.getAll());
    }

    @Override
    public List<AccountDto> getAllByPerson(Long personId) {
        List<Account> accounts = accountRepository.getAllByPerson(personId);
        return accountMapper.mapDtos(accounts);
    }

    @Override
    public List<AccountDto> getByState(AccountStateDto accountStateDto) {
        Long personId = accountStateDto.getPersonId();
        boolean isActive = Boolean.parseBoolean(accountStateDto.getIsActive());
        List<Account> accounts = accountRepository.getByState(personId, isActive);
        return accountMapper.mapDtos(accounts);
    }

    @Override
    public List<AccountDto> getAllByType(AccountTypeDto accountTypeDto) {
        AccountType accountType = AccountType.getByValue(accountTypeDto.getType());
        List<Account> accounts = accountRepository.getAllByType(accountType);
        return accountMapper.mapDtos(accounts);
    }

    @Transactional
    @Override
    public void update(Long personId, AccountDto accountDto) {
        Account account = getAccount(personId, mappingUtil.mapId(accountDto));
        accountMapper.update(accountDto, account);
        accountRepository.save(personId, account);
    }

    @Transactional
    @Override
    public AccountDto create(Long personId, AccountDto accountDto) {
        Account account = accountMapper.map(accountDto);
        account.setOpenDate(LocalDateTime.now());
        account.setIsActive(true);
        if (Objects.nonNull(account.getClosedDate()))
            account.setClosedDate(null);
        account = accountRepository.save(personId, account);
        return accountMapper.mapDto(account);
    }

    @Transactional
    @Override
    public void delete(Long personId, Long accountId) {
        int result = accountRepository.delete(personId, accountId);
        validationUtil.checkNotFoundWithId(result != 0, accountId);
    }

    @Transactional
    @Override
    public void deleteAllInactiveByPerson(Long personId) {
        int result = accountRepository.deleteAllInactiveByPerson(personId);
        validationUtil.checkNotFound(result != 0);
    }

    @Transactional
    @Override
    public void deleteAllInactive() {
        int result = accountRepository.deleteAllInactive();
        validationUtil.checkNotFound(result != 0);
    }

    private Account getAccount(Long personId, Long accountId) {
        return getEntityFromOptional(accountRepository.get(personId, accountId), accountId);
    }
}
