package ru.smaginv.debtmanager.service.account;

import ru.smaginv.debtmanager.web.dto.account.AccountDto;
import ru.smaginv.debtmanager.web.dto.account.AccountInfoDto;
import ru.smaginv.debtmanager.web.dto.account.AccountStateDto;
import ru.smaginv.debtmanager.web.dto.account.AccountTypeDto;

import java.util.List;

public interface AccountService {

    AccountDto get(Long personId, Long accountId);

    AccountInfoDto getWithOperations(Long personId, Long accountId);

    List<AccountDto> getAll();

    List<AccountDto> getAllByPerson(Long personId);

    List<AccountDto> getByState(AccountStateDto accountStateDto);

    List<AccountDto> getAllByType(AccountTypeDto accountTypeDto);

    void update(Long personId, AccountDto accountDto);

    AccountDto create(Long personId, AccountDto accountDto);

    void delete(Long personId, Long accountId);

    void deleteAllInactiveByPerson(Long personId);

    void deleteAllInactive();
}
