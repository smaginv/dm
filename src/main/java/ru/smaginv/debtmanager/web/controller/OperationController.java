package ru.smaginv.debtmanager.web.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.smaginv.debtmanager.service.operation.OperationService;
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
    public ResponseEntity<OperationDto> get(@Valid @RequestBody OperationIdDto operationIdDto) {
        log.info("get operation by id: {}", operationIdDto);
        return ResponseEntity.ok(operationService.get(operationIdDto));
    }

    @GetMapping(
            value = "/by-account"
    )
    public ResponseEntity<List<OperationDto>> getAllByAccount(@Valid @RequestBody AccountIdDto accountIdDto) {
        log.info("get all by account: {}", accountIdDto);
        return ResponseEntity.ok(operationService.getAllByAccount(accountIdDto));
    }

    @GetMapping(
            consumes = MediaType.ALL_VALUE
    )
    public ResponseEntity<List<OperationDto>> getAll() {
        log.info("get all operations");
        return ResponseEntity.ok(operationService.getAll());
    }

    @GetMapping(
            value = "/by-type"
    )
    public ResponseEntity<List<OperationDto>> getByType(@Valid @RequestBody OperationTypeDto operationTypeDto) {
        log.info("get operations by: {}", operationTypeDto);
        return ResponseEntity.ok(operationService.getByType(operationTypeDto));
    }

    @GetMapping(
            value = "/find"
    )
    public ResponseEntity<List<OperationDto>> find(@Valid @RequestBody OperationSearchDto operationSearchDto) {
        log.info("search operation: {}", operationSearchDto);
        return ResponseEntity.ok(operationService.find(operationSearchDto));
    }

    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody OperationUpdateDto operationUpdateDto) {
        log.info("update operation: {}", operationUpdateDto);
        operationService.update(operationUpdateDto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<OperationDto> create(@Valid @RequestBody OperationDto operationDto) {
        log.info("create operation: {}", operationDto);
        OperationDto created = operationService.create(operationDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .build()
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping(
            value = "/close-account"
    )
    public ResponseEntity<AccountDto> closeAccount(@Valid @RequestBody OperationDto operationDto) {
        log.info("close account by operation: {}", operationDto);
        return ResponseEntity.ok(operationService.closeAccount(operationDto));
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@Valid @RequestBody OperationIdsDto operationIdsDto) {
        log.info("delete operation with id: {}, for account: {}",
                operationIdsDto.getId(), operationIdsDto.getAccountId());
        operationService.delete(operationIdsDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(
            value = "/by-account"
    )
    public ResponseEntity<?> deleteAllByAccount(@Valid @RequestBody AccountIdDto accountIdDto) {
        log.info("delete all operations by account: {}", accountIdDto);
        operationService.deleteAllByAccount(accountIdDto);
        return ResponseEntity.noContent().build();
    }
}
