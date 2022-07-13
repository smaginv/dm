package ru.smaginv.debtmanager.dm.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.smaginv.debtmanager.dm.message.Message;
import ru.smaginv.debtmanager.dm.message.MessageService;
import ru.smaginv.debtmanager.dm.service.account.AccountService;
import ru.smaginv.debtmanager.dm.web.AuthUser;
import ru.smaginv.debtmanager.dm.web.dto.account.*;
import ru.smaginv.debtmanager.dm.web.dto.person.PersonIdDto;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Log4j2
@RestController
@RequestMapping(
        value = "/accounts",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
@Tag(name = "Account controller")
public class AccountController {

    private final AccountService accountService;
    private final MessageService messageService;

    @Autowired
    public AccountController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping(
            value = "/by-id/get"
    )
    @Operation(summary = "Get account by id")
    public ResponseEntity<AccountDto> get(@AuthenticationPrincipal AuthUser authUser,
                                          @Valid @RequestBody AccountIdDto accountIdDto,
                                          HttpServletRequest request) {
        log.info("get account: {}", accountIdDto);
        AccountDto account = accountService.get(authUser.getId(), accountIdDto);
        Message message = messageService.createMessage(authUser.getUsername(), request.getRequestURI(),
                request.getMethod(), accountIdDto, account);
        messageService.sendMessage(message);
        return ResponseEntity.ok(account);
    }

    @PostMapping(
            value = "/with-operations/get"
    )
    @Operation(summary = "Get an account by id with operations")
    public ResponseEntity<AccountInfoDto> getWithOperations(@AuthenticationPrincipal AuthUser authUser,
                                                            @Valid @RequestBody AccountIdDto accountIdDto,
                                                            HttpServletRequest request) {
        log.info("get account: {}, with operations", accountIdDto);
        AccountInfoDto accountInfo = accountService.getWithOperations(authUser.getId(), accountIdDto);
        Message message = messageService.createMessage(authUser.getUsername(), request.getRequestURI(),
                request.getMethod(), accountIdDto, accountInfo);
        messageService.sendMessage(message);
        return ResponseEntity.ok(accountInfo);
    }

    @GetMapping(
            consumes = MediaType.ALL_VALUE
    )
    @Operation(summary = "Get all accounts")
    public ResponseEntity<List<AccountDto>> getAll(@AuthenticationPrincipal AuthUser authUser,
                                                   HttpServletRequest request) {
        log.info("get all accounts");
        List<AccountDto> accounts = accountService.getAll(authUser.getId());
        Message message = messageService.createMessage(authUser.getUsername(), request.getRequestURI(),
                request.getMethod(), RequestEntity.EMPTY, accounts);
        messageService.sendMessage(message);
        return ResponseEntity.ok(accounts);
    }

    @PostMapping(
            value = "/by-person/get"
    )
    @Operation(summary = "Get all accounts by person")
    public ResponseEntity<List<AccountDto>> getAllByPerson(@AuthenticationPrincipal AuthUser authUser,
                                                           @Valid @RequestBody PersonIdDto personIdDto,
                                                           HttpServletRequest request) {
        log.info("get all accounts by person: {}", personIdDto);
        List<AccountDto> accounts = accountService.getAllByPerson(authUser.getId(), personIdDto);
        Message message = messageService.createMessage(authUser.getUsername(), request.getRequestURI(),
                request.getMethod(), personIdDto, accounts);
        messageService.sendMessage(message);
        return ResponseEntity.ok(accounts);
    }

    @PostMapping(
            value = "/by-status/get"
    )
    @Operation(summary = "Get all accounts by status")
    public ResponseEntity<List<AccountDto>> getAllByStatus(@AuthenticationPrincipal AuthUser authUser,
                                                           @Valid @RequestBody AccountStatusDto accountStatusDto,
                                                           HttpServletRequest request) {
        log.info("get by status: {}", accountStatusDto);
        List<AccountDto> accounts = accountService.getAllByStatus(authUser.getId(), accountStatusDto);
        Message message = messageService.createMessage(authUser.getUsername(), request.getRequestURI(),
                request.getMethod(), accountStatusDto, accounts);
        messageService.sendMessage(message);
        return ResponseEntity.ok(accounts);
    }

    @PostMapping(
            value = "/by-type/get"
    )
    @Operation(summary = "Get all accounts by type")
    public ResponseEntity<List<AccountDto>> getAllByType(@AuthenticationPrincipal AuthUser authUser,
                                                         @Valid @RequestBody AccountTypeDto accountTypeDto,
                                                         HttpServletRequest request) {
        log.info("get by type: {}", accountTypeDto);
        List<AccountDto> accounts = accountService.getAllByType(authUser.getId(), accountTypeDto);
        Message message = messageService.createMessage(authUser.getUsername(), request.getRequestURI(),
                request.getMethod(), accountTypeDto, accounts);
        messageService.sendMessage(message);
        return ResponseEntity.ok(accounts);
    }

    @PostMapping(
            value = "/active/by-type/total-amount/get"
    )
    @Operation(summary = "Get active accounts total amount by type")
    public ResponseEntity<AmountDto> getActiveAccountsTotalAmountByType(
            @AuthenticationPrincipal AuthUser authUser,
            @Valid @RequestBody AccountTypeDto accountTypeDto,
            HttpServletRequest request) {
        log.info("get active accounts total amount by type: {}", accountTypeDto);
        AmountDto amount = accountService.getActiveAccountsTotalAmountByType(authUser.getId(), accountTypeDto);
        Message message = messageService.createMessage(authUser.getUsername(), request.getRequestURI(),
                request.getMethod(), accountTypeDto, amount);
        messageService.sendMessage(message);
        return ResponseEntity.ok(amount);
    }

    @PostMapping(
            value = "/inactive/by-type/total-amount/get"
    )
    @Operation(summary = "Get inactive accounts total amount by type")
    public ResponseEntity<AmountDto> getInactiveAccountsTotalAmountByType(
            @AuthenticationPrincipal AuthUser authUser,
            @Valid @RequestBody AccountTypeDto accountTypeDto,
            HttpServletRequest request) {
        log.info("get inactive accounts total amount by type: {}", accountTypeDto);
        AmountDto amount = accountService.getInactiveAccountsTotalAmountByType(authUser.getId(), accountTypeDto);
        Message message = messageService.createMessage(authUser.getUsername(), request.getRequestURI(),
                request.getMethod(), accountTypeDto, amount);
        messageService.sendMessage(message);
        return ResponseEntity.ok(amount);
    }

    @PatchMapping
    @Operation(summary = "Update account")
    public ResponseEntity<?> update(@AuthenticationPrincipal AuthUser authUser,
                                    @Valid @RequestBody AccountUpdateDto accountUpdateDto,
                                    HttpServletRequest request) {
        log.info("update account: {}", accountUpdateDto);
        accountService.update(authUser.getId(), accountUpdateDto);
        Message message = messageService.createMessage(authUser.getUsername(), request.getRequestURI(),
                request.getMethod(), accountUpdateDto, ResponseEntity.EMPTY);
        messageService.sendMessage(message);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    @Operation(summary = "Create account")
    public ResponseEntity<AccountDto> create(@AuthenticationPrincipal AuthUser authUser,
                                             @Valid @RequestBody AccountDto accountDto,
                                             HttpServletRequest request) {
        log.info("create account: {}", accountDto);
        AccountDto created = accountService.create(authUser.getId(), accountDto);
        Message message = messageService.createMessage(authUser.getUsername(), request.getRequestURI(),
                request.getMethod(), accountDto, created);
        messageService.sendMessage(message);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PostMapping(
            value = "/by-id/delete"
    )
    @Operation(summary = "Delete account by id")
    public ResponseEntity<AccountDto> delete(@AuthenticationPrincipal AuthUser authUser,
                                             @Valid @RequestBody AccountIdDto accountIdDto,
                                             HttpServletRequest request) {
        log.info("delete account: {}", accountIdDto);
        accountService.delete(authUser.getId(), accountIdDto);
        Message message = messageService.createMessage(authUser.getUsername(), request.getRequestURI(),
                request.getMethod(), accountIdDto, ResponseEntity.EMPTY);
        messageService.sendMessage(message);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(
            value = "/inactive/by-person/delete"
    )
    @Operation(summary = "Delete all inactive accounts by person")
    private ResponseEntity<?> deleteAllInactiveByPerson(@AuthenticationPrincipal AuthUser authUser,
                                                        @Valid @RequestBody PersonIdDto personIdDto,
                                                        HttpServletRequest request) {
        log.info("delete all inactive accounts by person: {}", personIdDto);
        accountService.deleteAllInactiveByPerson(authUser.getId(), personIdDto);
        Message message = messageService.createMessage(authUser.getUsername(), request.getRequestURI(),
                request.getMethod(), personIdDto, ResponseEntity.EMPTY);
        messageService.sendMessage(message);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(
            value = "/inactive/delete",
            consumes = MediaType.ALL_VALUE
    )
    @Operation(summary = "Delete all inactive accounts")
    private ResponseEntity<?> deleteAllInactive(@AuthenticationPrincipal AuthUser authUser,
                                                HttpServletRequest request) {
        log.info("delete all inactive accounts");
        accountService.deleteAllInactive(authUser.getId());
        Message message = messageService.createMessage(authUser.getUsername(), request.getRequestURI(),
                request.getMethod(), RequestEntity.EMPTY, ResponseEntity.EMPTY);
        messageService.sendMessage(message);
        return ResponseEntity.noContent().build();
    }
}
