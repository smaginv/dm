package ru.smaginv.debtmanager.lm.util;

import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

@Component
public class MappingUtil {

    @Named("localDateToDateTime")
    public LocalDateTime localDateToDateTime(LocalDate localDate) {
        if (Objects.isNull(localDate))
            return null;
        return LocalDateTime.of(localDate, LocalTime.MIN);
    }
}
