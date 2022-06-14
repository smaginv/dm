package ru.smaginv.debtmanager.dm.web.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.smaginv.debtmanager.dm.entity.operation.Operation;
import ru.smaginv.debtmanager.dm.util.MappingUtil;
import ru.smaginv.debtmanager.dm.web.dto.operation.OperationDto;
import ru.smaginv.debtmanager.dm.web.dto.operation.OperationUpdateDto;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = MappingUtil.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface OperationMapper {

    @Mapping(target = "account", ignore = true)
    @Mapping(target = "operationType", source = "type")
    @Mapping(target = "operDate", source = "operDate", qualifiedByName = "parseStringToLocalDateTime")
    Operation map(OperationDto operationDto);

    @Mapping(target = "accountId", ignore = true)
    @Mapping(target = "type", source = "operationType")
    @Mapping(target = "operDate", source = "operDate", qualifiedByName = "formatDateToString")
    OperationDto mapDto(Operation operation);

    List<OperationDto> mapDtos(List<Operation> operations);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "account", ignore = true)
    @Mapping(target = "operationType", ignore = true)
    @Mapping(target = "operDate", source = "operDate", qualifiedByName = "parseStringToLocalDateTime")
    void update(OperationUpdateDto operationUpdateDto, @MappingTarget Operation operation);
}
