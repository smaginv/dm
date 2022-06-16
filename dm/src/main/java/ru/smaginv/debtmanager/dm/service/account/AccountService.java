package ru.smaginv.debtmanager.dm.service.account;

import ru.smaginv.debtmanager.dm.web.dto.account.*;
import ru.smaginv.debtmanager.dm.web.dto.person.PersonIdDto;

import java.util.List;

public interface AccountService {

    AccountDto get(Long userId, AccountIdDto accountIdDto);

    AccountInfoDto getWithOperations(Long userId, AccountIdDto accountIdDto);

    List<AccountDto> getAll(Long userId);

    List<AccountDto> getAllByPerson(Long userId, PersonIdDto personIdDto);

    List<AccountDto> getAllByStatus(Long userId, AccountStatusDto accountStatusDto);

    List<AccountDto> getAllByType(Long userId, AccountTypeDto accountTypeDto);

    AmountDto getActiveAccountsTotalAmountByType(Long userId, AccountTypeDto accountTypeDto);

    AmountDto getInactiveAccountsTotalAmountByType(Long userId, AccountTypeDto accountTypeDto);

    void update(Long userId, AccountUpdateDto accountUpdateDto);

    AccountDto create(Long userId, AccountDto accountDto);

    void delete(Long userId, AccountIdDto accountIdDto);

    void deleteAllInactiveByPerson(Long userId, PersonIdDto personIdDto);

    void deleteAllInactive(Long userId);
}
