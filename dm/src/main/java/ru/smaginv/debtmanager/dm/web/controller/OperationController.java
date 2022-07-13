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
import ru.smaginv.debtmanager.dm.service.operation.OperationService;
import ru.smaginv.debtmanager.dm.web.AuthUser;
import ru.smaginv.debtmanager.dm.web.dto.account.AccountDto;
import ru.smaginv.debtmanager.dm.web.dto.account.AccountIdDto;
import ru.smaginv.debtmanager.dm.web.dto.operation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Log4j2
@RestController
@RequestMapping(
        value = "/operations",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
@Tag(name = "Operation controller")
public class OperationController {

    private final OperationService operationService;
    private final MessageService messageService;

    @Autowired
    public OperationController(OperationService operationService, MessageService messageService) {
        this.operationService = operationService;
        this.messageService = messageService;
    }

    @PostMapping(
            value = "/by-id/get"
    )
    @Operation(summary = "Get operation by id")
    public ResponseEntity<OperationDto> get(@AuthenticationPrincipal AuthUser authUser,
                                            @Valid @RequestBody OperationIdDto operationIdDto,
                                            HttpServletRequest request) {
        log.info("get operation by id: {}", operationIdDto);
        OperationDto operation = operationService.get(authUser.getId(), operationIdDto);
        Message message = messageService.createMessage(authUser.getUsername(), request.getRequestURI(),
                request.getMethod(), operationIdDto, operation);
        messageService.sendMessage(message);
        return ResponseEntity.ok(operation);
    }

    @PostMapping(
            value = "/by-account/get"
    )
    @Operation(summary = "Get all operations by account")
    public ResponseEntity<List<OperationDto>> getAllByAccount(@AuthenticationPrincipal AuthUser authUser,
                                                              @Valid @RequestBody AccountIdDto accountIdDto,
                                                              HttpServletRequest request) {
        log.info("get all by account: {}", accountIdDto);
        List<OperationDto> operations = operationService.getAllByAccount(authUser.getId(), accountIdDto);
        Message message = messageService.createMessage(authUser.getUsername(), request.getRequestURI(),
                request.getMethod(), accountIdDto, operations);
        messageService.sendMessage(message);
        return ResponseEntity.ok(operations);
    }

    @GetMapping(
            consumes = MediaType.ALL_VALUE
    )
    @Operation(summary = "Get all operations")
    public ResponseEntity<List<OperationDto>> getAll(@AuthenticationPrincipal AuthUser authUser,
                                                     HttpServletRequest request) {
        log.info("get all operations");
        List<OperationDto> operations = operationService.getAll(authUser.getId());
        Message message = messageService.createMessage(authUser.getUsername(), request.getRequestURI(),
                request.getMethod(), RequestEntity.EMPTY, operations);
        messageService.sendMessage(message);
        return ResponseEntity.ok(operations);
    }

    @PostMapping(
            value = "/by-type/get"
    )
    @Operation(summary = "Get all operations by type")
    public ResponseEntity<List<OperationDto>> getByType(@AuthenticationPrincipal AuthUser authUser,
                                                        @Valid @RequestBody OperationTypeDto operationTypeDto,
                                                        HttpServletRequest request) {
        log.info("get operations by: {}", operationTypeDto);
        List<OperationDto> operations = operationService.getByType(authUser.getId(), operationTypeDto);
        Message message = messageService.createMessage(authUser.getUsername(), request.getRequestURI(),
                request.getMethod(), operationTypeDto, operations);
        messageService.sendMessage(message);
        return ResponseEntity.ok(operations);
    }

    @PostMapping(
            value = "/find"
    )
    @Operation(summary = "Find operations")
    public ResponseEntity<List<OperationDto>> find(@AuthenticationPrincipal AuthUser authUser,
                                                   @Valid @RequestBody OperationSearchDto operationSearchDto,
                                                   HttpServletRequest request) {
        log.info("search operation: {}", operationSearchDto);
        List<OperationDto> operations = operationService.find(authUser.getId(), operationSearchDto);
        Message message = messageService.createMessage(authUser.getUsername(), request.getRequestURI(),
                request.getMethod(), operationSearchDto, operations);
        messageService.sendMessage(message);
        return ResponseEntity.ok(operations);
    }

    @PutMapping
    @Operation(summary = "Update operations")
    public ResponseEntity<?> update(@AuthenticationPrincipal AuthUser authUser,
                                    @Valid @RequestBody OperationUpdateDto operationUpdateDto,
                                    HttpServletRequest request) {
        log.info("update operation: {}", operationUpdateDto);
        operationService.update(authUser.getId(), operationUpdateDto);
        Message message = messageService.createMessage(authUser.getUsername(), request.getRequestURI(),
                request.getMethod(), operationUpdateDto, ResponseEntity.EMPTY);
        messageService.sendMessage(message);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    @Operation(summary = "Create operations")
    public ResponseEntity<OperationDto> create(@AuthenticationPrincipal AuthUser authUser,
                                               @Valid @RequestBody OperationDto operationDto,
                                               HttpServletRequest request) {
        log.info("create operation: {}", operationDto);
        OperationDto created = operationService.create(authUser.getId(), operationDto);
        Message message = messageService.createMessage(authUser.getUsername(), request.getRequestURI(),
                request.getMethod(), operationDto, created);
        messageService.sendMessage(message);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PostMapping(
            value = "/account/close"
    )
    @Operation(summary = "Close account")
    public ResponseEntity<AccountDto> closeAccount(@AuthenticationPrincipal AuthUser authUser,
                                                   @Valid @RequestBody OperationDto operationDto,
                                                   HttpServletRequest request) {
        log.info("close account by operation: {}", operationDto);
        AccountDto account = operationService.closeAccount(authUser.getId(), operationDto);
        Message message = messageService.createMessage(authUser.getUsername(), request.getRequestURI(),
                request.getMethod(), operationDto, account);
        messageService.sendMessage(message);
        return ResponseEntity.ok(account);
    }

    @PostMapping(
            value = "/by-id/delete"
    )
    @Operation(summary = "Delete operation by id and account")
    public ResponseEntity<?> delete(@AuthenticationPrincipal AuthUser authUser,
                                    @Valid @RequestBody OperationIdsDto operationIdsDto,
                                    HttpServletRequest request) {
        log.info("delete operation with id: {}, for account: {}",
                operationIdsDto.getId(), operationIdsDto.getAccountId());
        operationService.delete(authUser.getId(), operationIdsDto);
        Message message = messageService.createMessage(authUser.getUsername(), request.getRequestURI(),
                request.getMethod(), operationIdsDto, ResponseEntity.EMPTY);
        messageService.sendMessage(message);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(
            value = "/by-account/delete"
    )
    @Operation(summary = "Delete all operations by account")
    public ResponseEntity<?> deleteAllByAccount(@AuthenticationPrincipal AuthUser authUser,
                                                @Valid @RequestBody AccountIdDto accountIdDto,
                                                HttpServletRequest request) {
        log.info("delete all operations by account: {}", accountIdDto);
        operationService.deleteAllByAccount(authUser.getId(), accountIdDto);
        Message message = messageService.createMessage(authUser.getUsername(), request.getRequestURI(),
                request.getMethod(), accountIdDto, ResponseEntity.EMPTY);
        messageService.sendMessage(message);
        return ResponseEntity.noContent().build();
    }
}
