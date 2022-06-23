package ru.smaginv.debtmanager.lm.repository;

import ru.smaginv.debtmanager.lm.entity.Log;

import java.time.LocalDate;
import java.util.List;

public interface LogRepository {

    List<Log> getOnDate(LocalDate date);

    List<Log> getBetweenDates(LocalDate startDate, LocalDate endDate);

    List<Log> find(Log log);

    int delete(Long logId);

    int deleteOnDate(LocalDate date);

    int deleteBetweenDates(LocalDate startDate, LocalDate endDate);

    int deleteByConditionOnDate(Log log);

    int deleteAllByCondition(Log log);

    void save(Log log);
}
