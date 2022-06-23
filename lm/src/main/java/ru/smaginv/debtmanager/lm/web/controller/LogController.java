package ru.smaginv.debtmanager.lm.web.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.smaginv.debtmanager.lm.entity.Log;
import ru.smaginv.debtmanager.lm.service.LogService;
import ru.smaginv.debtmanager.lm.web.dto.BetweenDatesDto;
import ru.smaginv.debtmanager.lm.web.dto.DateDto;
import ru.smaginv.debtmanager.lm.web.dto.LogIdDto;
import ru.smaginv.debtmanager.lm.web.dto.LogSearchDto;

import javax.validation.Valid;
import java.util.List;

@Log4j2
@RestController
@RequestMapping(
        value = "/logs",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
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
    public ResponseEntity<List<Log>> getToday() {
        log.info("get all today logs");
        return ResponseEntity.ok(logService.getToday());
    }

    @GetMapping(
            value = "/on-date"
    )
    public ResponseEntity<List<Log>> getOnDate(@Valid @RequestBody DateDto dateDto) {
        log.info("get logs on date: {}", dateDto.getDate());
        return ResponseEntity.ok(logService.getOnDate(dateDto));
    }

    @GetMapping(
            value = "/between-dates"
    )
    public ResponseEntity<List<Log>> getBetweenDates(@Valid @RequestBody BetweenDatesDto betweenDatesDto) {
        log.info("get between dates: {} - {}", betweenDatesDto.getStartDate(), betweenDatesDto.getEndDate());
        return ResponseEntity.ok(logService.getBetweenDates(betweenDatesDto));
    }

    @GetMapping(
            value = "/find"
    )
    public ResponseEntity<List<Log>> find(@RequestBody LogSearchDto logSearchDto) {
        log.info("find log by: {}", logSearchDto);
        return ResponseEntity.ok(logService.find(logSearchDto));
    }

    @DeleteMapping(
            value = "/by-id"
    )
    public ResponseEntity<?> deleteById(@Valid @RequestBody LogIdDto logIdDto) {
        log.info("delete log by: {}", logIdDto);
        logService.delete(logIdDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(
            value = "/on-date"
    )
    public ResponseEntity<?> deleteOnDate(@Valid @RequestBody DateDto dateDto) {
        log.info("delete all logs on date: {}", dateDto);
        logService.deleteOnDate(dateDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(
            value = "/between-dates"
    )
    public ResponseEntity<?> deleteBetweenDates(@Valid @RequestBody BetweenDatesDto betweenDatesDto) {
        log.info("delete all logs between: {}", betweenDatesDto);
        logService.deleteBetweenDates(betweenDatesDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(
            value = "/by-condition-on-date"
    )
    public ResponseEntity<?> deleteByConditionOnDate(@Valid @RequestBody LogSearchDto logSearchDto) {
        log.info("delete by: {}", logSearchDto);
        logService.deleteByConditionOnDate(logSearchDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(
            value = "/all-by-condition"
    )
    public ResponseEntity<?> deleteAllByCondition(@RequestBody LogSearchDto logSearchDto) {
        log.info("delete by: {}", logSearchDto);
        logService.deleteAllByCondition(logSearchDto);
        return ResponseEntity.noContent().build();
    }
}
