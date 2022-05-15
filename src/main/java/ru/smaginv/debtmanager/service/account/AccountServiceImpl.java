package ru.smaginv.debtmanager.service.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.smaginv.debtmanager.entity.account.Account;
import ru.smaginv.debtmanager.repository.account.AccountRepository;
import ru.smaginv.debtmanager.service.operation.OperationService;
import ru.smaginv.debtmanager.web.dto.account.AccountDto;
import ru.smaginv.debtmanager.web.dto.account.AccountIdDto;
import ru.smaginv.debtmanager.web.dto.account.AccountInfoDto;
import ru.smaginv.debtmanager.web.dto.operation.OperationDto;
import ru.smaginv.debtmanager.web.dto.person.PersonIdDto;
import ru.smaginv.debtmanager.web.mapping.AccountMapper;

import java.util.List;

import static ru.smaginv.debtmanager.util.MappingUtil.mapId;
import static ru.smaginv.debtmanager.util.ValidationUtil.*;
import static ru.smaginv.debtmanager.util.entity.EntityUtil.getEntityFromOptional;

@Service
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final OperationService operationService;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, AccountMapper accountMapper,
                              OperationService operationService) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.operationService = operationService;
    }

    @Override
    public AccountDto get(AccountIdDto accountIdDto, PersonIdDto personIdDto) {
        Account account = get(mapId(accountIdDto), mapId(personIdDto));
        return accountMapper.mapDto(account);
    }

    @Override
    public AccountInfoDto getWithOperations(AccountIdDto accountIdDto, PersonIdDto personIdDto) {
        Account account = get(mapId(accountIdDto), mapId(personIdDto));
        List<OperationDto> operations = operationService.getAllByAccount(accountIdDto);
        AccountInfoDto accountInfoDto = accountMapper.mapInfoDto(account);
        accountInfoDto.setOperations(operations);
        return accountInfoDto;
    }

    @Override
    public List<AccountDto> getAll() {
        return accountMapper.mapDtos(accountRepository.getAll());
    }

    @Override
    public List<AccountDto> getAllByPerson(PersonIdDto personIdDto) {
        List<Account> accounts = accountRepository.getAllByPerson(mapId(personIdDto));
        return accountMapper.mapDtos(accounts);
    }

    @Override
    public List<AccountDto> getAllActive() {
        return accountMapper.mapDtos(accountRepository.getAllActive());
    }

    @Override
    public List<AccountDto> getAllActiveByPerson(PersonIdDto personIdDto) {
        List<Account> accounts = accountRepository.getAllActiveByPerson(mapId(personIdDto));
        return accountMapper.mapDtos(accounts);
    }

    @Override
    public List<AccountDto> getAllInactive() {
        return accountMapper.mapDtos(accountRepository.getAllInactive());
    }

    @Override
    public List<AccountDto> getAllInactiveByPerson(PersonIdDto personIdDto) {
        List<Account> accounts = accountRepository.getAllInactiveByPerson(mapId(personIdDto));
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
    public AccountDto update(AccountDto accountDto, PersonIdDto personIdDto) {
        Account account = get(mapId(accountDto), mapId(personIdDto));
        accountMapper.update(accountDto, account);
        return accountMapper.mapDto(accountRepository.save(account, mapId(personIdDto)));
    }

    @Transactional
    @Override
    public AccountDto save(AccountDto accountDto, PersonIdDto personIdDto) {
        checkIsNew(accountDto);
        Account account = accountRepository.save(accountMapper.map(accountDto), mapId(personIdDto));
        return accountMapper.mapDto(account);
    }

    @Override
    public void delete(AccountIdDto accountIdDto, PersonIdDto personIdDto) {
        int result = accountRepository.delete(mapId(accountIdDto), mapId(personIdDto));
        checkNotFoundWithId(result != 0, accountIdDto);
    }

    @Override
    public void deleteAllByPerson(PersonIdDto personIdDto) {
        int result = accountRepository.deleteAllByPerson(mapId(personIdDto));
        checkNotFound(result != 0);
    }

    @Override
    public void deleteAllInactiveByPerson(PersonIdDto personIdDto) {
        int result = accountRepository.deleteAllInactiveByPerson(mapId(personIdDto));
        checkNotFound(result != 0);
    }

    @Override
    public void deleteAllInactive() {
        int result = accountRepository.deleteAllInactive();
        checkNotFound(result != 0);
    }

    private Account get(Long accountId, Long personId) {
        return getEntityFromOptional(accountRepository.get(accountId, personId), accountId);
    }
}
