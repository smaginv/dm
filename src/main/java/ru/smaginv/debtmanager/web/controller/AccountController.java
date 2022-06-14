package ru.smaginv.debtmanager.web.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.smaginv.debtmanager.service.account.AccountService;
import ru.smaginv.debtmanager.web.AuthUser;
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
    public ResponseEntity<AccountDto> get(@AuthenticationPrincipal AuthUser authUser,
                                          @Valid @RequestBody AccountIdDto accountIdDto) {
        log.info("get account: {}", accountIdDto);
        return ResponseEntity.ok(accountService.get(authUser.getId(), accountIdDto));
    }

    @GetMapping(
            value = "/with-operations"
    )
    public ResponseEntity<AccountInfoDto> getWithOperations(@AuthenticationPrincipal AuthUser authUser,
                                                            @Valid @RequestBody AccountIdDto accountIdDto) {
        log.info("get account: {}, with operations", accountIdDto);
        return ResponseEntity.ok(accountService.getWithOperations(authUser.getId(), accountIdDto));
    }

    @GetMapping(
            consumes = MediaType.ALL_VALUE
    )
    public ResponseEntity<List<AccountDto>> getAll(@AuthenticationPrincipal AuthUser authUser) {
        log.info("get all accounts");
        return ResponseEntity.ok(accountService.getAll(authUser.getId()));
    }

    @GetMapping(
            value = "/by-person"
    )
    public ResponseEntity<List<AccountDto>> getAllByPerson(@AuthenticationPrincipal AuthUser authUser,
                                                           @Valid @RequestBody PersonIdDto personIdDto) {
        log.info("get all accounts by person: {}", personIdDto);
        return ResponseEntity.ok(accountService.getAllByPerson(authUser.getId(), personIdDto));
    }

    @GetMapping(
            value = "/by-status"
    )
    public ResponseEntity<List<AccountDto>> getAllByStatus(@AuthenticationPrincipal AuthUser authUser,
                                                           @Valid @RequestBody AccountStatusDto accountStatusDto) {
        log.info("get by status: {}", accountStatusDto);
        return ResponseEntity.ok(accountService.getAllByStatus(authUser.getId(), accountStatusDto));
    }

    @GetMapping(
            value = "/by-type"
    )
    public ResponseEntity<List<AccountDto>> getAllByType(@AuthenticationPrincipal AuthUser authUser,
                                                         @Valid @RequestBody AccountTypeDto accountTypeDto) {
        log.info("get by type: {}", accountTypeDto);
        return ResponseEntity.ok(accountService.getAllByType(authUser.getId(), accountTypeDto));
    }

    @GetMapping(
            value = "/active/by-type/total-amount"
    )
    public ResponseEntity<String> getActiveAccountsTotalAmountByType(
            @AuthenticationPrincipal AuthUser authUser,
            @Valid @RequestBody AccountTypeDto accountTypeDto) {
        log.info("get active accounts total amount by type: {}", accountTypeDto);
        return ResponseEntity.ok(accountService.getActiveAccountsTotalAmountByType(authUser.getId(), accountTypeDto));
    }

    @GetMapping(
            value = "/inactive/by-type/total-amount"
    )
    public ResponseEntity<String> getInactiveAccountsTotalAmountByType(
            @AuthenticationPrincipal AuthUser authUser,
            @Valid @RequestBody AccountTypeDto accountTypeDto) {
        log.info("get inactive accounts total amount by type: {}", accountTypeDto);
        return ResponseEntity.ok(accountService.getInactiveAccountsTotalAmountByType(authUser.getId(), accountTypeDto));
    }

    @PatchMapping
    public ResponseEntity<?> update(@AuthenticationPrincipal AuthUser authUser,
                                    @Valid @RequestBody AccountUpdateDto accountUpdateDto) {
        log.info("update account: {}", accountUpdateDto);
        accountService.update(authUser.getId(), accountUpdateDto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<AccountDto> create(@AuthenticationPrincipal AuthUser authUser,
                                             @Valid @RequestBody AccountDto accountDto) {
        log.info("create account: {}", accountDto);
        AccountDto created = accountService.create(authUser.getId(), accountDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .build()
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@AuthenticationPrincipal AuthUser authUser,
                                    @Valid @RequestBody AccountIdDto accountIdDto) {
        log.info("delete account: {}", accountIdDto);
        accountService.delete(authUser.getId(), accountIdDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(
            value = "/inactive/by-person"
    )
    private ResponseEntity<?> deleteAllInactiveByPerson(@AuthenticationPrincipal AuthUser authUser,
                                                        @Valid @RequestBody PersonIdDto personIdDto) {
        log.info("delete all inactive accounts by person: {}", personIdDto);
        accountService.deleteAllInactiveByPerson(authUser.getId(), personIdDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(
            value = "/inactive",
            consumes = MediaType.ALL_VALUE
    )
    private ResponseEntity<?> deleteAllInactive(@AuthenticationPrincipal AuthUser authUser) {
        log.info("delete all inactive accounts");
        accountService.deleteAllInactive(authUser.getId());
        return ResponseEntity.noContent().build();
    }
}
