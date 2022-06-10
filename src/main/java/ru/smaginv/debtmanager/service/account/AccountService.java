package ru.smaginv.debtmanager.service.account;

import ru.smaginv.debtmanager.web.dto.account.*;
import ru.smaginv.debtmanager.web.dto.person.PersonIdDto;

import java.util.List;

public interface AccountService {

    AccountDto get(AccountIdDto accountIdDto);

    AccountInfoDto getWithOperations(AccountIdDto accountIdDto);

    List<AccountDto> getAll();

    List<AccountDto> getAllByPerson(PersonIdDto personIdDto);

    List<AccountDto> getAllByStatus(AccountStatusDto accountStatusDto);

    List<AccountDto> getAllByType(AccountTypeDto accountTypeDto);

    String getActiveAccountsTotalAmountByType(AccountTypeDto accountTypeDto);

    String getInactiveAccountsTotalAmountByType(AccountTypeDto accountTypeDto);

    void update(AccountUpdateDto accountUpdateDto);

    AccountDto create(AccountDto accountDto);

    void delete(AccountIdDto accountIdDto);

    void deleteAllInactiveByPerson(PersonIdDto personIdDto);

    void deleteAllInactive();
}
