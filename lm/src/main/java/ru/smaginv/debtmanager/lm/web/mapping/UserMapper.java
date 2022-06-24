package ru.smaginv.debtmanager.lm.web.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.smaginv.debtmanager.lm.entity.user.User;
import ru.smaginv.debtmanager.lm.web.dto.user.UserDto;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    User map(UserDto userDto);

    UserDto mapDto(User user);

    List<UserDto> mapDtos(List<User> users);
}
