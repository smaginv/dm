package ru.smaginv.debtmanager.web.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.smaginv.debtmanager.entity.contact.Contact;
import ru.smaginv.debtmanager.web.dto.contact.ContactDto;
import ru.smaginv.debtmanager.web.dto.contact.ContactSearchDto;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ContactMapper {

    @Mapping(source = "type", target = "contactType")
    Contact map(ContactDto contactDto);

    @Mapping(source = "type", target = "contactType")
    Contact map(ContactSearchDto contactSearchDto);

    @Mapping(source = "contactType", target = "type")
    ContactDto mapDto(Contact contact);

    List<ContactDto> mapDtos(List<Contact> contacts);

    @Mapping(source = "type", target = "contactType")
    void update(ContactDto contactDto, @MappingTarget Contact contact);
}