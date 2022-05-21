package ru.smaginv.debtmanager.web.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.smaginv.debtmanager.service.account.AccountService;
import ru.smaginv.debtmanager.util.validation.ValidationUtil;
import ru.smaginv.debtmanager.web.dto.account.AccountDto;
import ru.smaginv.debtmanager.web.dto.account.AccountInfoDto;
import ru.smaginv.debtmanager.web.dto.account.AccountStateDto;
import ru.smaginv.debtmanager.web.dto.account.AccountTypeDto;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Log4j2
@RestController
@RequestMapping(
        value = "/people",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class AccountController {

    private final AccountService accountService;
    private final ValidationUtil validationUtil;

    @Autowired
    public AccountController(AccountService accountService, ValidationUtil validationUtil) {
        this.accountService = accountService;
        this.validationUtil = validationUtil;
    }

    @GetMapping(
            value = "/person/{personId}/accounts/{accountId}",
            consumes = MediaType.ALL_VALUE
    )
    public ResponseEntity<AccountDto> get(@PathVariable Long personId,
                                          @PathVariable Long accountId) {
        log.info("get account by id: {} for person: {}", accountId, personId);
        return ResponseEntity.ok(accountService.get(personId, accountId));
    }

    @GetMapping(
            value = "/person/{personId}/accounts/with-operations/{accountId}",
            consumes = MediaType.ALL_VALUE
    )
    public ResponseEntity<AccountInfoDto> getWithOperations(@PathVariable Long personId,
                                                            @PathVariable Long accountId) {
        log.info("get account with operations by id: {} for person: {}", accountId, personId);
        return ResponseEntity.ok(accountService.getWithOperations(personId, accountId));
    }

    @GetMapping(
            value = "/accounts",
            consumes = MediaType.ALL_VALUE
    )
    public ResponseEntity<List<AccountDto>> getAll() {
        log.info("get all accounts");
        return ResponseEntity.ok(accountService.getAll());
    }

    @GetMapping(
            value = "/person/{personId}/accounts",
            consumes = MediaType.ALL_VALUE
    )
    public ResponseEntity<List<AccountDto>> getAllByPerson(@PathVariable Long personId) {
        log.info("get all by person: {}", personId);
        return ResponseEntity.ok(accountService.getAllByPerson(personId));
    }

    @GetMapping(
            value = "/accounts/by-state"
    )
    public ResponseEntity<List<AccountDto>> getByState(@Valid @RequestBody AccountStateDto accountStateDto) {
        log.info("activeDto: {}", accountStateDto);
        return ResponseEntity.ok(accountService.getByState(accountStateDto));
    }

    @GetMapping(
            value = "/accounts/type"
    )
    public ResponseEntity<List<AccountDto>> getAllByType(@Valid @RequestBody AccountTypeDto accountType) {
        log.info("get all accounts by type: {}", accountType);
        return ResponseEntity.ok(accountService.getAllByType(accountType));
    }

    @PatchMapping(
            value = "/person/{personId}/accounts/{accountId}"
    )
    public ResponseEntity<?> update(@PathVariable Long personId,
                                    @PathVariable String accountId,
                                    @Valid @RequestBody AccountDto accountDto) {
        validationUtil.assureIdConsistent(accountDto, accountId);
        log.info("update account: {}, with id: {}", accountDto, accountId);
        accountService.update(personId, accountDto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(
            value = "/person/{personId}/accounts"
    )
    public ResponseEntity<AccountDto> create(@PathVariable Long personId,
                                             @Valid @RequestBody AccountDto accountDto) {
        validationUtil.checkIsNew(accountDto);
        log.info("create account: {}, for person: {}", accountDto, personId);
        AccountDto created = accountService.create(personId, accountDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{accountId}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @DeleteMapping(
            value = "/person/{personId}/accounts/{accountId}",
            consumes = MediaType.ALL_VALUE
    )
    public ResponseEntity<?> delete(@PathVariable Long personId, @PathVariable Long accountId) {
        log.info("delete account with id: {}, for person with id: {}", personId, accountId);
        accountService.delete(personId, accountId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(
            value = "/person/{personId}/accounts/inactive",
            consumes = MediaType.ALL_VALUE
    )
    private ResponseEntity<?> deleteAllInactiveByPerson(@PathVariable Long personId) {
        log.info("delete all inactive accounts by person with id: {}", personId);
        accountService.deleteAllInactiveByPerson(personId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(
            value = "/accounts/inactive",
            consumes = MediaType.ALL_VALUE
    )
    private ResponseEntity<?> deleteAllInactive() {
        log.info("delete all inactive accounts");
        accountService.deleteAllInactive();
        return ResponseEntity.noContent().build();
    }
}
