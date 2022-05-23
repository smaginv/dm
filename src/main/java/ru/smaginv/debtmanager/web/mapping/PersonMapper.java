package ru.smaginv.debtmanager.web.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.smaginv.debtmanager.entity.person.Person;
import ru.smaginv.debtmanager.web.dto.person.PersonDto;
import ru.smaginv.debtmanager.web.dto.person.PersonInfoDto;
import ru.smaginv.debtmanager.web.dto.person.PersonSearchDto;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface PersonMapper {

    @Mapping(target = "comment", ignore = true)
    Person map(PersonSearchDto personSearchDto);

    Person map(PersonDto personDto);

    PersonDto mapDto(Person person);

    @Mapping(target = "accounts", ignore = true)
    @Mapping(target = "contacts", ignore = true)
    PersonInfoDto mapInfoDto(Person person);

    List<PersonDto> mapDtos(List<Person> people);

    void update(PersonDto personDto, @MappingTarget Person person);
}
