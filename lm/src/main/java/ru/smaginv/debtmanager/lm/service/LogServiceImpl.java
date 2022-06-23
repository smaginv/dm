package ru.smaginv.debtmanager.lm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.smaginv.debtmanager.lm.entity.Log;
import ru.smaginv.debtmanager.lm.repository.LogRepository;
import ru.smaginv.debtmanager.lm.util.exception.NotFoundException;
import ru.smaginv.debtmanager.lm.web.dto.BetweenDatesDto;
import ru.smaginv.debtmanager.lm.web.dto.DateDto;
import ru.smaginv.debtmanager.lm.web.dto.LogIdDto;
import ru.smaginv.debtmanager.lm.web.dto.LogSearchDto;
import ru.smaginv.debtmanager.lm.web.mapping.LogMapper;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;
    private final LogMapper logMapper;

    @Autowired
    public LogServiceImpl(LogRepository logRepository, LogMapper logMapper) {
        this.logRepository = logRepository;
        this.logMapper = logMapper;
    }

    @Override
    public List<Log> getToday() {
        return logRepository.getOnDate(LocalDate.now());
    }

    @Override
    public List<Log> getOnDate(DateDto dateDto) {
        return logRepository.getOnDate(dateDto.getDate());
    }

    @Override
    public List<Log> getBetweenDates(BetweenDatesDto betweenDatesDto) {
        return logRepository.getBetweenDates(betweenDatesDto.getStartDate(), betweenDatesDto.getEndDate());
    }

    @Override
    public List<Log> find(LogSearchDto logSearchDto) {
        return logRepository.find(logMapper.map(logSearchDto));
    }

    @Transactional
    @Override
    public void delete(LogIdDto logIdDto) {
        checkNotFound(logRepository.delete(logIdDto.getLogId()) != 0);
    }

    @Transactional
    @Override
    public void deleteOnDate(DateDto dateDto) {
        checkNotFound(logRepository.deleteOnDate(dateDto.getDate()) != 0);
    }

    @Transactional
    @Override
    public void deleteBetweenDates(BetweenDatesDto betweenDatesDto) {
        checkNotFound(logRepository.deleteBetweenDates(betweenDatesDto.getStartDate(),
                betweenDatesDto.getEndDate()) != 0);
    }

    @Transactional
    @Override
    public void deleteByConditionOnDate(LogSearchDto logSearchDto) {
        checkNotFound(logRepository.deleteByConditionOnDate(logMapper.map(logSearchDto)) != 0);
    }

    @Transactional
    @Override
    public void deleteAllByCondition(LogSearchDto logSearchDto) {
        checkNotFound(logRepository.deleteAllByCondition(logMapper.map(logSearchDto)) != 0);
    }

    public void checkNotFound(boolean found) {
        if (!found)
            throw new NotFoundException();
    }
}
