package ru.smaginv.debtmanager.dm.web.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.smaginv.debtmanager.dm.entity.user.User;
import ru.smaginv.debtmanager.dm.web.dto.user.UserDto;
import ru.smaginv.debtmanager.dm.web.dto.user.UserUpdateDto;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UserMapper {

    @Mapping(target = "status", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User map(UserDto userDto);

    UserDto mapDto(User user);

    List<UserDto> mapDtos(List<User> users);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "roles", ignore = true)
    void update(UserUpdateDto userUpdateDto, @MappingTarget User user);
}
