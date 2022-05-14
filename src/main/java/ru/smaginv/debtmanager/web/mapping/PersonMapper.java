package ru.smaginv.debtmanager.web.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ru.smaginv.debtmanager.entity.person.Person;
import ru.smaginv.debtmanager.web.dto.person.PersonDto;
import ru.smaginv.debtmanager.web.dto.person.PersonInfoDto;
import ru.smaginv.debtmanager.web.dto.person.PersonSearchDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    Person map(PersonSearchDto personSearchDto);

    Person map(PersonDto personDto);

    PersonDto mapDto(Person person);

    PersonInfoDto mapInfoDto(Person person);

    List<PersonDto> mapDtos(List<Person> people);

    void updatePerson(PersonDto personDto, @MappingTarget Person person);
}
