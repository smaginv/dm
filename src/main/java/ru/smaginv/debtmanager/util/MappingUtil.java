package ru.smaginv.debtmanager.util;

import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.smaginv.debtmanager.config.PropertiesConfig;
import ru.smaginv.debtmanager.web.dto.HasIdDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Component
public class MappingUtil {

    private final String dateTimePattern;

    @Autowired
    public MappingUtil(PropertiesConfig propertiesConfig) {
        this.dateTimePattern = propertiesConfig.pattern().getDateTime();
    }

    @Named("formatDateToString")
    public String formatDateToString(LocalDateTime dateTime) {
        if (Objects.isNull(dateTime))
            return null;
        return DateTimeFormatter.ofPattern(dateTimePattern).format(dateTime);
    }

    @Named("parseStringToLocalDateTime")
    public LocalDateTime parseStringToLocalDateTime(String dateTime) {
        if (Objects.isNull(dateTime))
            return null;
        return LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(dateTimePattern));
    }

    public Long mapId(HasIdDto entity) {
        if (Objects.isNull(entity) || Objects.isNull(entity.getId()))
            return null;
        return Long.valueOf(entity.getId().trim());
    }

    public Long mapId(String id) {
        if (Objects.isNull(id))
            return null;
        return Long.valueOf(id.trim());
    }
}
