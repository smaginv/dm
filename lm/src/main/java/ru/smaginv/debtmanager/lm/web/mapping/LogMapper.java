package ru.smaginv.debtmanager.lm.web.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.smaginv.debtmanager.lm.entity.log.Log;
import ru.smaginv.debtmanager.lm.util.MappingUtil;
import ru.smaginv.debtmanager.lm.web.dto.log.LogSearchDto;

@Mapper(
        componentModel = "spring",
        uses = MappingUtil.class
)
public interface LogMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "timestamp", source = "date", qualifiedByName = "localDateToDateTime")
    @Mapping(target = "requestBody", ignore = true)
    @Mapping(target = "responseBody", ignore = true)
    Log map(LogSearchDto logSearchDto);
}
