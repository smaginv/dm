package ru.smaginv.debtmanager.web.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.smaginv.debtmanager.entity.operation.Operation;
import ru.smaginv.debtmanager.util.MappingUtil;
import ru.smaginv.debtmanager.web.dto.operation.OperationDto;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = MappingUtil.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface OperationMapper {

    @Mapping(source = "type", target = "operationType")
    Operation map(OperationDto operationDto);

    @Mapping(source = "operationType", target = "type")
    @Mapping(source = "operDate", target = "operDate", qualifiedByName = "formatDateToString")
    OperationDto mapDto(Operation operation);

    List<OperationDto> mapDtos(List<Operation> operations);

    @Mapping(source = "type", target = "operationType")
    void update(OperationDto operationDto, @MappingTarget Operation operation);
}
