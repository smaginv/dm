package ru.smaginv.debtmanager.service.account;

import ru.smaginv.debtmanager.web.dto.account.AccountDto;
import ru.smaginv.debtmanager.web.dto.account.AccountIdDto;
import ru.smaginv.debtmanager.web.dto.account.AccountInfoDto;
import ru.smaginv.debtmanager.web.dto.person.PersonIdDto;

import java.util.List;

public interface AccountService {

    AccountDto get(AccountIdDto accountIdDto, PersonIdDto personIdDto);

    AccountInfoDto getWithOperations(AccountIdDto accountIdDto, PersonIdDto personIdDto);

    List<AccountDto> getAll();

    List<AccountDto> getAllByPerson(PersonIdDto personIdDto);

    List<AccountDto> getAllActive();

    List<AccountDto> getAllActiveByPerson(PersonIdDto personIdDto);

    List<AccountDto> getAllInactive();

    List<AccountDto> getAllInactiveByPerson(PersonIdDto personIdDto);

    List<AccountDto> getAllDebit();

    List<AccountDto> getAllCredit();

    AccountDto update(AccountDto accountDto, PersonIdDto personIdDto);

    AccountDto save(AccountDto accountDto, PersonIdDto personIdDto);

    void delete(AccountIdDto accountIdDto, PersonIdDto personIdDto);

    void deleteAllByPerson(PersonIdDto personIdDto);

    void deleteAllInactiveByPerson(PersonIdDto personIdDto);

    void deleteAllInactive();
}
