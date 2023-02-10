package ru.smaginv.debtmanager.lm.repository.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.smaginv.debtmanager.lm.entity.log.Log;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Repository
public class LogRepositoryImpl implements LogRepository {

    private final LogRepositoryJpa logRepository;

    @Autowired
    public LogRepositoryImpl(LogRepositoryJpa logRepository) {
        this.logRepository = logRepository;
    }

    @Override
    public List<Log> getOnDate(LocalDate date) {
        return logRepository.getOnDate(Date.valueOf(date));
    }

    @Override
    public List<Log> getBetweenDates(LocalDate startDate, LocalDate endDate) {
        return logRepository.getBetweenDates(Date.valueOf(startDate), Date.valueOf(endDate));
    }

    @Override
    public List<Log> find(Log log) {
        return logRepository.find(log, getDate(log));
    }

    @Override
    public int delete(Long logId) {
        return logRepository.delete(logId);
    }

    @Override
    public int deleteOnDate(LocalDate date) {
        return logRepository.deleteOnDate(Date.valueOf(date));
    }

    @Override
    public int deleteBetweenDates(LocalDate startDate, LocalDate endDate) {
        return logRepository.deleteBetweenDates(Date.valueOf(startDate), Date.valueOf(endDate));
    }

    @Override
    public int deleteByConditionOnDate(Log log) {
        return logRepository.deleteByConditionOnDate(log, getDate(log));
    }

    @Override
    public int deleteAllByCondition(Log log) {
        return logRepository.deleteAllByCondition(log);
    }

    @Transactional
    @Override
    public void save(Log log) {
        logRepository.save(log);
    }

    private Date getDate(Log log) {
        Date date = null;
        if (Objects.nonNull(log.getTimestamp())) {
            date = Date.valueOf(log.getTimestamp().toLocalDate());
            log.setTimestamp(null);
        }
        return date;
    }
}
