package ru.smaginv.debtmanager.lm.service.log;

import ru.smaginv.debtmanager.lm.entity.log.Log;
import ru.smaginv.debtmanager.lm.web.dto.log.BetweenDatesDto;
import ru.smaginv.debtmanager.lm.web.dto.log.DateDto;
import ru.smaginv.debtmanager.lm.web.dto.log.LogIdDto;
import ru.smaginv.debtmanager.lm.web.dto.log.LogSearchDto;

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
