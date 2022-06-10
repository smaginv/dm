package ru.smaginv.debtmanager.web.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.smaginv.debtmanager.service.account.AccountService;
import ru.smaginv.debtmanager.web.dto.account.*;
import ru.smaginv.debtmanager.web.dto.person.PersonIdDto;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Log4j2
@RestController
@RequestMapping(
        value = "/accounts",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<AccountDto> get(@Valid @RequestBody AccountIdDto accountIdDto) {
        log.info("get account: {}", accountIdDto);
        return ResponseEntity.ok(accountService.get(accountIdDto));
    }

    @GetMapping(
            value = "/with-operations"
    )
    public ResponseEntity<AccountInfoDto> getWithOperations(@Valid @RequestBody AccountIdDto accountIdDto) {
        log.info("get account: {}, with operations", accountIdDto);
        return ResponseEntity.ok(accountService.getWithOperations(accountIdDto));
    }

    @GetMapping(
            consumes = MediaType.ALL_VALUE
    )
    public ResponseEntity<List<AccountDto>> getAll() {
        log.info("get all accounts");
        return ResponseEntity.ok(accountService.getAll());
    }

    @GetMapping(
            value = "/by-person"
    )
    public ResponseEntity<List<AccountDto>> getAllByPerson(@Valid @RequestBody PersonIdDto personIdDto) {
        log.info("get all accounts by person: {}", personIdDto);
        return ResponseEntity.ok(accountService.getAllByPerson(personIdDto));
    }

    @GetMapping(
            value = "/by-status"
    )
    public ResponseEntity<List<AccountDto>> getAllByStatus(@Valid @RequestBody AccountStatusDto accountStatusDto) {
        log.info("get by status: {}", accountStatusDto);
        return ResponseEntity.ok(accountService.getAllByStatus(accountStatusDto));
    }

    @GetMapping(
            value = "/by-type"
    )
    public ResponseEntity<List<AccountDto>> getAllByType(@Valid @RequestBody AccountTypeDto accountTypeDto) {
        log.info("get by type: {}", accountTypeDto);
        return ResponseEntity.ok(accountService.getAllByType(accountTypeDto));
    }

    @GetMapping(
            value = "/active/by-type/total-amount"
    )
    public ResponseEntity<String> getActiveAccountsTotalAmountByType(
            @Valid @RequestBody AccountTypeDto accountTypeDto) {
        log.info("get active accounts total amount by type: {}", accountTypeDto);
        return ResponseEntity.ok(accountService.getActiveAccountsTotalAmountByType(accountTypeDto));
    }

    @GetMapping(
            value = "/inactive/by-type/total-amount"
    )
    public ResponseEntity<String> getInactiveAccountsTotalAmountByType(
            @Valid @RequestBody AccountTypeDto accountTypeDto) {
        log.info("get inactive accounts total amount by type: {}", accountTypeDto);
        return ResponseEntity.ok(accountService.getInactiveAccountsTotalAmountByType(accountTypeDto));
    }

    @PatchMapping
    public ResponseEntity<?> update(@Valid @RequestBody AccountUpdateDto accountUpdateDto) {
        log.info("update account: {}", accountUpdateDto);
        accountService.update(accountUpdateDto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<AccountDto> create(@Valid @RequestBody AccountDto accountDto) {
        log.info("create account: {}", accountDto);
        AccountDto created = accountService.create(accountDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .build()
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@Valid @RequestBody AccountIdDto accountIdDto) {
        log.info("delete account: {}", accountIdDto);
        accountService.delete(accountIdDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(
            value = "/inactive/by-person"
    )
    private ResponseEntity<?> deleteAllInactiveByPerson(@Valid @RequestBody PersonIdDto personIdDto) {
        log.info("delete all inactive accounts by person: {}", personIdDto);
        accountService.deleteAllInactiveByPerson(personIdDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(
            value = "/inactive",
            consumes = MediaType.ALL_VALUE
    )
    private ResponseEntity<?> deleteAllInactive() {
        log.info("delete all inactive accounts");
        accountService.deleteAllInactive();
        return ResponseEntity.noContent().build();
    }
}
