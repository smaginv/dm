package ru.smaginv.debtmanager.lm.message;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.smaginv.debtmanager.lm.entity.log.Log;

@Mapper(
        componentModel = "spring"
)
public interface MessageMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "method", source = "httpMethod")
    Log map(Message message);
}
