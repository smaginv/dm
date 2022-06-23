package ru.smaginv.debtmanager.lm.service;

import ru.smaginv.debtmanager.lm.entity.Log;
import ru.smaginv.debtmanager.lm.web.dto.BetweenDatesDto;
import ru.smaginv.debtmanager.lm.web.dto.DateDto;
import ru.smaginv.debtmanager.lm.web.dto.LogIdDto;
import ru.smaginv.debtmanager.lm.web.dto.LogSearchDto;

import java.util.List;

public interface LogService {

    List<Log> getToday();

    List<Log> getOnDate(DateDto dateDto);

    List<Log> getBetweenDates(BetweenDatesDto betweenDatesDto);

    List<Log> find(LogSearchDto logSearchDto);

    void delete(LogIdDto logIdDto);

    void deleteOnDate(DateDto dateDto);

    void deleteBetweenDates(BetweenDatesDto betweenDatesDto);

    void deleteByConditionOnDate(LogSearchDto logSearchDto);

    void deleteAllByCondition(LogSearchDto logSearchDto);
}
