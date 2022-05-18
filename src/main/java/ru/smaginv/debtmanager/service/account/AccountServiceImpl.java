package ru.smaginv.debtmanager.service.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.smaginv.debtmanager.entity.account.Account;
import ru.smaginv.debtmanager.repository.account.AccountRepository;
import ru.smaginv.debtmanager.service.operation.OperationService;
import ru.smaginv.debtmanager.util.MappingUtil;
import ru.smaginv.debtmanager.util.validation.ValidationUtil;
import ru.smaginv.debtmanager.web.dto.account.AccountDto;
import ru.smaginv.debtmanager.web.dto.account.AccountInfoDto;
import ru.smaginv.debtmanager.web.dto.operation.OperationDto;
import ru.smaginv.debtmanager.web.mapping.AccountMapper;

import java.util.List;

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
    public AccountDto get(Long accountId, Long personId) {
        Account account = getAccount(accountId, personId);
        return accountMapper.mapDto(account);
    }

    @Override
    public AccountInfoDto getWithOperations(Long accountId, Long personId) {
        Account account = getAccount(accountId, personId);
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
    public List<AccountDto> getAllActive() {
        return accountMapper.mapDtos(accountRepository.getAllActive());
    }

    @Override
    public List<AccountDto> getAllActiveByPerson(Long personId) {
        List<Account> accounts = accountRepository.getAllActiveByPerson(personId);
        return accountMapper.mapDtos(accounts);
    }

    @Override
    public List<AccountDto> getAllInactive() {
        return accountMapper.mapDtos(accountRepository.getAllInactive());
    }

    @Override
    public List<AccountDto> getAllInactiveByPerson(Long personId) {
        List<Account> accounts = accountRepository.getAllInactiveByPerson(personId);
        return accountMapper.mapDtos(accounts);
    }

    @Override
    public List<AccountDto> getAllDebit() {
        return accountMapper.mapDtos(accountRepository.getAllDebit());
    }

    @Override
    public List<AccountDto> getAllCredit() {
        return accountMapper.mapDtos(accountRepository.getAllCredit());
    }

    @Transactional
    @Override
    public AccountDto update(AccountDto accountDto, Long personId) {
        Account account = getAccount(mappingUtil.mapId(accountDto), personId);
        accountMapper.update(accountDto, account);
        return accountMapper.mapDto(accountRepository.save(account, personId));
    }

    @Transactional
    @Override
    public AccountDto create(AccountDto accountDto, Long personId) {
        validationUtil.checkIsNew(accountDto);
        Account account = accountRepository.save(accountMapper.map(accountDto), personId);
        return accountMapper.mapDto(account);
    }

    @Override
    public void delete(Long accountId, Long personId) {
        int result = accountRepository.delete(accountId, personId);
        validationUtil.checkNotFoundWithId(result != 0, accountId);
    }

    @Override
    public void deleteAllByPerson(Long personId) {
        int result = accountRepository.deleteAllByPerson(personId);
        validationUtil.checkNotFound(result != 0);
    }

    @Override
    public void deleteAllInactiveByPerson(Long personId) {
        int result = accountRepository.deleteAllInactiveByPerson(personId);
        validationUtil.checkNotFound(result != 0);
    }

    @Override
    public void deleteAllInactive() {
        int result = accountRepository.deleteAllInactive();
        validationUtil.checkNotFound(result != 0);
    }

    private Account getAccount(Long accountId, Long personId) {
        return getEntityFromOptional(accountRepository.get(accountId, personId), accountId);
    }
}
