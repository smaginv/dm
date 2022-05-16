package ru.smaginv.debtmanager.service.account;

import ru.smaginv.debtmanager.web.dto.account.AccountDto;
import ru.smaginv.debtmanager.web.dto.account.AccountInfoDto;

import java.util.List;

public interface AccountService {

    AccountDto get(Long accountId, Long personId);

    AccountInfoDto getWithOperations(Long accountId, Long personId);

    List<AccountDto> getAll();

    List<AccountDto> getAllByPerson(Long personId);

    List<AccountDto> getAllActive();

    List<AccountDto> getAllActiveByPerson(Long personId);

    List<AccountDto> getAllInactive();

    List<AccountDto> getAllInactiveByPerson(Long personId);

    List<AccountDto> getAllDebit();

    List<AccountDto> getAllCredit();

    AccountDto update(AccountDto accountDto, Long personId);

    AccountDto create(AccountDto accountDto, Long personId);

    void delete(Long accountId, Long personId);

    void deleteAllByPerson(Long personId);

    void deleteAllInactiveByPerson(Long personId);

    void deleteAllInactive();
}
