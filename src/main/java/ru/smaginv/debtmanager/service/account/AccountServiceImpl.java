package ru.smaginv.debtmanager.service.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.smaginv.debtmanager.entity.account.Account;
import ru.smaginv.debtmanager.entity.account.AccountType;
import ru.smaginv.debtmanager.entity.operation.Operation;
import ru.smaginv.debtmanager.repository.account.AccountRepository;
import ru.smaginv.debtmanager.repository.operation.OperationRepository;
import ru.smaginv.debtmanager.util.MappingUtil;
import ru.smaginv.debtmanager.util.exception.AccountActiveException;
import ru.smaginv.debtmanager.util.validation.ValidationUtil;
import ru.smaginv.debtmanager.web.dto.account.*;
import ru.smaginv.debtmanager.web.mapping.AccountMapper;
import ru.smaginv.debtmanager.web.mapping.OperationMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
    public AccountDto get(Long personId, Long accountId) {
        Account account = getAccount(personId, accountId);
        return accountMapper.mapDto(account);
    }

    @Override
    public AccountInfoDto getWithOperations(Long personId, Long accountId) {
        Account account = getAccount(personId, accountId);
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
    public List<AccountDto> getAllByPerson(Long personId) {
        List<Account> accounts = accountRepository.getAllByPerson(personId);
        return accountMapper.mapDtos(accounts);
    }

    @Override
    public List<AccountDto> getByState(AccountStateDto accountStateDto) {
        Long personId = accountStateDto.getPersonId();
        boolean isActive = Boolean.parseBoolean(accountStateDto.getActive());
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
    public void update(Long personId, AccountUpdateDto accountUpdateDto) {
        Account account = getAccount(personId, mappingUtil.mapId(accountUpdateDto));
        accountMapper.update(accountUpdateDto, account);
        accountRepository.save(personId, account);
    }

    @Transactional
    @Override
    public AccountDto create(Long personId, AccountDto accountDto) {
        Account account = accountMapper.map(accountDto);
        account.setOpenDate(LocalDateTime.now());
        account.setActive(true);
        account = accountRepository.save(personId, account);
        return accountMapper.mapDto(account);
    }

    @Transactional
    @Override
    public void delete(Long personId, Long accountId) {
        Account account = getAccount(personId, accountId);
        if (account.getAmount().compareTo(BigDecimal.ZERO) != 0)
            throw new AccountActiveException("amount of account must be 0.0");
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
