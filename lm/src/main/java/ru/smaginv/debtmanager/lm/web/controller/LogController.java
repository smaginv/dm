package ru.smaginv.debtmanager.lm.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.smaginv.debtmanager.lm.entity.log.Log;
import ru.smaginv.debtmanager.lm.service.log.LogService;
import ru.smaginv.debtmanager.lm.web.dto.log.BetweenDatesDto;
import ru.smaginv.debtmanager.lm.web.dto.log.DateDto;
import ru.smaginv.debtmanager.lm.web.dto.log.LogIdDto;
import ru.smaginv.debtmanager.lm.web.dto.log.LogSearchDto;

import javax.validation.Valid;
import java.util.List;

@Log4j2
@RestController
@RequestMapping(
        value = "/logs",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
@Tag(name = "Log controller")
public class LogController {

    private final LogService logService;

    @Autowired
    public LogController(LogService logService) {
        this.logService = logService;
    }

    @GetMapping(
            value = "/today",
            consumes = MediaType.ALL_VALUE
    )
    @Operation(summary = "Get all of today's logs")
    public ResponseEntity<List<Log>> getToday() {
        log.info("get all today logs");
        return ResponseEntity.ok(logService.getToday());
    }

    @PostMapping(
            value = "/on-date"
    )
    @Operation(summary = "Get all logs on the date")
    public ResponseEntity<List<Log>> getOnDate(@Valid @RequestBody DateDto dateDto) {
        log.info("get logs on date: {}", dateDto.getDate());
        return ResponseEntity.ok(logService.getOnDate(dateDto));
    }

    @PostMapping(
            value = "/between-dates"
    )
    @Operation(summary = "Get all logs between dates")
    public ResponseEntity<List<Log>> getBetweenDates(@Valid @RequestBody BetweenDatesDto betweenDatesDto) {
        log.info("get between dates: {} - {}", betweenDatesDto.getStartDate(), betweenDatesDto.getEndDate());
        return ResponseEntity.ok(logService.getBetweenDates(betweenDatesDto));
    }

    @PostMapping(
            value = "/find"
    )
    @Operation(summary = "Find logs")
    public ResponseEntity<List<Log>> find(@RequestBody LogSearchDto logSearchDto) {
        log.info("find log by: {}", logSearchDto);
        return ResponseEntity.ok(logService.find(logSearchDto));
    }

    @PostMapping(
            value = "/by-id/delete"
    )
    @Operation(summary = "Delete log by id")
    public ResponseEntity<?> deleteById(@Valid @RequestBody LogIdDto logIdDto) {
        log.info("delete log by: {}", logIdDto);
        logService.delete(logIdDto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(
            value = "/on-date/delete"
    )
    @Operation(summary = "Delete all logs on date")
    public ResponseEntity<?> deleteOnDate(@Valid @RequestBody DateDto dateDto) {
        log.info("delete all logs on date: {}", dateDto);
        logService.deleteOnDate(dateDto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(
            value = "/between-dates/delete"
    )
    @Operation(summary = "Delete all logs between dates")
    public ResponseEntity<?> deleteBetweenDates(@Valid @RequestBody BetweenDatesDto betweenDatesDto) {
        log.info("delete all logs between: {}", betweenDatesDto);
        logService.deleteBetweenDates(betweenDatesDto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(
            value = "/by-condition-on-date/delete"
    )
    @Operation(summary = "Delete logs by condition on date")
    public ResponseEntity<?> deleteByConditionOnDate(@Valid @RequestBody LogSearchDto logSearchDto) {
        log.info("delete by: {}", logSearchDto);
        logService.deleteByConditionOnDate(logSearchDto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(
            value = "/all-by-condition/delete"
    )
    @Operation(summary = "Delete all logs by condition")
    public ResponseEntity<?> deleteAllByCondition(@RequestBody LogSearchDto logSearchDto) {
        log.info("delete by: {}", logSearchDto);
        logService.deleteAllByCondition(logSearchDto);
        return ResponseEntity.noContent().build();
    }
}
