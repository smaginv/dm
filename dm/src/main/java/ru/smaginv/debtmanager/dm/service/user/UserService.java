package ru.smaginv.debtmanager.dm.service.user;

import ru.smaginv.debtmanager.dm.web.dto.user.*;

import java.util.List;

public interface UserService {

    UserDto get(UserIdDto userIdDto);

    UserDto getByUsername(String username);

    UserDto getByUsername(UsernameDto usernameDto);

    UserDto getByEmail(UserEmailDto userEmailDto);

    List<UserDto> getAll();

    List<UserDto> getAllByStatus(UserStatusDto userStatusDto);

    void update(UserUpdateDto userUpdateDto);

    UserDto setStatus(UserStatusDto userStatusDto);

    UserDto setRole(UserRoleDto userRoleDto);

    UserDto create(UserDto userDto);

    void delete(UserIdDto userIdDto);

    void deleteByEmail(UserEmailDto userEmailDto);
}
