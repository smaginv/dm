package ru.smaginv.debtmanager.web.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.smaginv.debtmanager.service.operation.OperationService;
import ru.smaginv.debtmanager.util.validation.ValidationUtil;
import ru.smaginv.debtmanager.web.dto.operation.OperationDto;
import ru.smaginv.debtmanager.web.dto.operation.OperationSearchDto;
import ru.smaginv.debtmanager.web.dto.operation.OperationTypeDto;

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
public class OperationController {

    private final OperationService operationService;
    private final ValidationUtil validationUtil;

    @Autowired
    public OperationController(OperationService operationService, ValidationUtil validationUtil) {
        this.operationService = operationService;
        this.validationUtil = validationUtil;
    }

    @GetMapping(
            value = "/accounts/{accountId}/operations/{operationId}",
            consumes = MediaType.ALL_VALUE
    )
    public ResponseEntity<OperationDto> get(@PathVariable Long accountId, @PathVariable Long operationId) {
        log.info("get operation by id: {} for account: {}", operationId, accountId);
        return ResponseEntity.ok(operationService.get(accountId, operationId));
    }

    @GetMapping(
            value = "/accounts/{accountId}/operations",
            consumes = MediaType.ALL_VALUE
    )
    public ResponseEntity<List<OperationDto>> getAllByAccount(@PathVariable Long accountId) {
        log.info("get all by account: {}", accountId);
        return ResponseEntity.ok(operationService.getAllByAccount(accountId));
    }

    @GetMapping(
            value = "/accounts/operations",
            consumes = MediaType.ALL_VALUE
    )
    public ResponseEntity<List<OperationDto>> getAll() {
        log.info("get all operations");
        return ResponseEntity.ok(operationService.getAll());
    }

    @GetMapping(
            value = "/accounts/operations/by-type"
    )
    public ResponseEntity<List<OperationDto>> getByType(@Valid @RequestBody OperationTypeDto operationTypeDto) {
        log.info("get operations by: {}", operationTypeDto);
        return ResponseEntity.ok(operationService.getByType(operationTypeDto));
    }

    @GetMapping(
            value = "/accounts/operations/find"
    )
    public ResponseEntity<List<OperationDto>> find(@Valid @RequestBody OperationSearchDto operationSearchDto) {
        log.info("search operation: {}", operationSearchDto);
        return ResponseEntity.ok(operationService.find(operationSearchDto));
    }

    @PatchMapping(
            value = "/accounts/{accountId}/operations/{operationId}"
    )
    public ResponseEntity<?> update(@PathVariable Long accountId,
                                    @PathVariable String operationId,
                                    @RequestBody OperationDto operationDto) {
        validationUtil.assureIdConsistent(operationDto, operationId);
        log.info("update operation: {}, with id: {}", operationDto, operationId);
        operationService.update(accountId, operationDto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(
            value = "/accounts/{accountId}/operations"
    )
    public ResponseEntity<OperationDto> create(@PathVariable Long accountId,
                                               @Valid @RequestBody OperationDto operationDto) {
        validationUtil.checkIsNew(operationDto);
        log.info("create operation: {}, for account: {}", operationDto, accountId);
        OperationDto created = operationService.create(accountId, operationDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{operationId}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @DeleteMapping(
            value = "/accounts/{accountId}/operations/{operationId}",
            consumes = MediaType.ALL_VALUE
    )
    public ResponseEntity<?> delete(@PathVariable Long accountId, @PathVariable Long operationId) {
        log.info("delete operation with id: {}, for account: {}", operationId, accountId);
        operationService.delete(accountId, operationId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(
            value = "/accounts/{accountId}/operations",
            consumes = MediaType.ALL_VALUE
    )
    public ResponseEntity<?> deleteAllByAccount(@PathVariable Long accountId) {
        log.info("delete all operations by account: {}", accountId);
        operationService.deleteAllByAccount(accountId);
        return ResponseEntity.noContent().build();
    }
}
