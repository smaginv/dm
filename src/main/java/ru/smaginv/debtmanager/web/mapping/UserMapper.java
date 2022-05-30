package ru.smaginv.debtmanager.web.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.smaginv.debtmanager.entity.user.User;
import ru.smaginv.debtmanager.web.dto.user.UserDto;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UserMapper {

    @Mapping(target = "userStatus", source = "status")
    User map(UserDto userDto);

    @Mapping(target = "status", source = "userStatus")
    UserDto mapDto(User user);

    List<UserDto> mapDtos(List<User> users);

    @Mapping(target = "userStatus", source = "status")
    void update(UserDto userDto, @MappingTarget User user);
}
