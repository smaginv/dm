package ru.smaginv.debtmanager.util;

import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.smaginv.debtmanager.config.AppProperties;
import ru.smaginv.debtmanager.web.dto.HasIdDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Component
public class MappingUtil {

    private final String datePattern;

    @Autowired
    public MappingUtil(AppProperties appProperties) {
        this.datePattern = appProperties.pattern().getDate();
    }

    @Named("formatDate")
    public String formatDate(LocalDateTime dateTime) {
        if (Objects.isNull(dateTime))
            return null;
        return DateTimeFormatter.ofPattern(datePattern).format(dateTime);
    }

    public Long mapId(HasIdDto entity) {
        return Long.valueOf(entity.getId().trim());
    }
}
