package ru.smaginv.debtmanager.web.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.smaginv.debtmanager.service.operation.OperationService;
import ru.smaginv.debtmanager.web.AuthUser;
import ru.smaginv.debtmanager.web.dto.account.AccountDto;
import ru.smaginv.debtmanager.web.dto.account.AccountIdDto;
import ru.smaginv.debtmanager.web.dto.operation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Log4j2
@RestController
@RequestMapping(
        value = "/operations",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class OperationController {

    private final OperationService operationService;

    @Autowired
    public OperationController(OperationService operationService) {
        this.operationService = operationService;
    }

    @GetMapping
    public ResponseEntity<OperationDto> get(@AuthenticationPrincipal AuthUser authUser,
                                            @Valid @RequestBody OperationIdDto operationIdDto) {
        log.info("get operation by id: {}", operationIdDto);
        return ResponseEntity.ok(operationService.get(authUser.getId(), operationIdDto));
    }

    @GetMapping(
            value = "/by-account"
    )
    public ResponseEntity<List<OperationDto>> getAllByAccount(@AuthenticationPrincipal AuthUser authUser,
                                                              @Valid @RequestBody AccountIdDto accountIdDto) {
        log.info("get all by account: {}", accountIdDto);
        return ResponseEntity.ok(operationService.getAllByAccount(authUser.getId(), accountIdDto));
    }

    @GetMapping(
            consumes = MediaType.ALL_VALUE
    )
    public ResponseEntity<List<OperationDto>> getAll(@AuthenticationPrincipal AuthUser authUser) {
        log.info("get all operations");
        return ResponseEntity.ok(operationService.getAll(authUser.getId()));
    }

    @GetMapping(
            value = "/by-type"
    )
    public ResponseEntity<List<OperationDto>> getByType(@AuthenticationPrincipal AuthUser authUser,
                                                        @Valid @RequestBody OperationTypeDto operationTypeDto) {
        log.info("get operations by: {}", operationTypeDto);
        return ResponseEntity.ok(operationService.getByType(authUser.getId(), operationTypeDto));
    }

    @GetMapping(
            value = "/find"
    )
    public ResponseEntity<List<OperationDto>> find(@AuthenticationPrincipal AuthUser authUser,
                                                   @Valid @RequestBody OperationSearchDto operationSearchDto) {
        log.info("search operation: {}", operationSearchDto);
        return ResponseEntity.ok(operationService.find(authUser.getId(), operationSearchDto));
    }

    @PutMapping
    public ResponseEntity<?> update(@AuthenticationPrincipal AuthUser authUser,
                                    @Valid @RequestBody OperationUpdateDto operationUpdateDto) {
        log.info("update operation: {}", operationUpdateDto);
        operationService.update(authUser.getId(), operationUpdateDto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<OperationDto> create(@AuthenticationPrincipal AuthUser authUser,
                                               @Valid @RequestBody OperationDto operationDto) {
        log.info("create operation: {}", operationDto);
        OperationDto created = operationService.create(authUser.getId(), operationDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .build()
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping(
            value = "/close-account"
    )
    public ResponseEntity<AccountDto> closeAccount(@AuthenticationPrincipal AuthUser authUser,
                                                   @Valid @RequestBody OperationDto operationDto) {
        log.info("close account by operation: {}", operationDto);
        return ResponseEntity.ok(operationService.closeAccount(authUser.getId(), operationDto));
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@AuthenticationPrincipal AuthUser authUser,
                                    @Valid @RequestBody OperationIdsDto operationIdsDto) {
        log.info("delete operation with id: {}, for account: {}",
                operationIdsDto.getId(), operationIdsDto.getAccountId());
        operationService.delete(authUser.getId(), operationIdsDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(
            value = "/by-account"
    )
    public ResponseEntity<?> deleteAllByAccount(@AuthenticationPrincipal AuthUser authUser,
                                                @Valid @RequestBody AccountIdDto accountIdDto) {
        log.info("delete all operations by account: {}", accountIdDto);
        operationService.deleteAllByAccount(authUser.getId(), accountIdDto);
        return ResponseEntity.noContent().build();
    }
}
