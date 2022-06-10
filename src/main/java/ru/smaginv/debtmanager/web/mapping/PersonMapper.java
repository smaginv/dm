package ru.smaginv.debtmanager.web.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.smaginv.debtmanager.entity.person.Person;
import ru.smaginv.debtmanager.web.dto.person.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface PersonMapper {

    @Mapping(target = "comment", ignore = true)
    @Mapping(target = "user", ignore = true)
    Person map(PersonSearchDto personSearchDto);

    @Mapping(target = "user", ignore = true)
    Person map(PersonDto personDto);

    @Mapping(target = "id", source = "personId")
    PersonIdDto mapIdDto(Long personId);

    PersonDto mapDto(Person person);

    @Mapping(target = "accounts", ignore = true)
    @Mapping(target = "contacts", ignore = true)
    PersonInfoDto mapInfoDto(Person person);

    List<PersonDto> mapDtos(List<Person> people);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    void update(PersonUpdateDto personUpdateDto, @MappingTarget Person person);
}
