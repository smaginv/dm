package ru.smaginv.debtmanager.web.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.smaginv.debtmanager.entity.contact.Contact;
import ru.smaginv.debtmanager.entity.contact.UniqueContact;
import ru.smaginv.debtmanager.web.dto.contact.AbstractContactDto;
import ru.smaginv.debtmanager.web.dto.contact.ContactDto;
import ru.smaginv.debtmanager.web.dto.contact.ContactUpdateDto;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ContactMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "person", ignore = true)
    @Mapping(target = "contactType", source = "type")
    Contact map(AbstractContactDto contactDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "contactId", source = "id")
    @Mapping(target = "contactValue", source = "value")
    UniqueContact mapUnique(Contact contact);

    @Mapping(target = "personId", ignore = true)
    @Mapping(target = "type", source = "contactType")
    ContactDto mapDto(Contact contact);

    List<ContactDto> mapDtos(List<Contact> contacts);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "person", ignore = true)
    @Mapping(target = "contactType", source = "type")
    void update(ContactUpdateDto contactUpdateDto, @MappingTarget Contact contact);
}
