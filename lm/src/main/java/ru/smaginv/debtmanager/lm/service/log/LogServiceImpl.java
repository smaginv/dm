package ru.smaginv.debtmanager.lm.service.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.smaginv.debtmanager.lm.entity.log.Log;
import ru.smaginv.debtmanager.lm.repository.log.LogRepository;
import ru.smaginv.debtmanager.lm.util.ValidationUtil;
import ru.smaginv.debtmanager.lm.web.dto.log.BetweenDatesDto;
import ru.smaginv.debtmanager.lm.web.dto.log.DateDto;
import ru.smaginv.debtmanager.lm.web.dto.log.LogIdDto;
import ru.smaginv.debtmanager.lm.web.dto.log.LogSearchDto;
import ru.smaginv.debtmanager.lm.web.mapping.LogMapper;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;
    private final LogMapper logMapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public LogServiceImpl(LogRepository logRepository, LogMapper logMapper,
                          ValidationUtil validationUtil) {
        this.logRepository = logRepository;
        this.logMapper = logMapper;
        this.validationUtil = validationUtil;
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
        validationUtil.checkNotFound(logRepository.delete(logIdDto.getId()) != 0);
    }

    @Transactional
    @Override
    public void deleteOnDate(DateDto dateDto) {
        validationUtil.checkNotFound(logRepository.deleteOnDate(dateDto.getDate()) != 0);
    }

    @Transactional
    @Override
    public void deleteBetweenDates(BetweenDatesDto betweenDatesDto) {
        validationUtil.checkNotFound(logRepository.deleteBetweenDates(betweenDatesDto.getStartDate(),
                betweenDatesDto.getEndDate()) != 0);
    }

    @Transactional
    @Override
    public void deleteByConditionOnDate(LogSearchDto logSearchDto) {
        validationUtil.checkNotFound(logRepository.deleteByConditionOnDate(logMapper.map(logSearchDto)) != 0);
    }

    @Transactional
    @Override
    public void deleteAllByCondition(LogSearchDto logSearchDto) {
        validationUtil.checkNotFound(logRepository.deleteAllByCondition(logMapper.map(logSearchDto)) != 0);
    }
}
