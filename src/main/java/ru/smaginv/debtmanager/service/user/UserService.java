package ru.smaginv.debtmanager.service.user;

import ru.smaginv.debtmanager.web.dto.user.UserDto;
import ru.smaginv.debtmanager.web.dto.user.UserEmailDto;
import ru.smaginv.debtmanager.web.dto.user.UserRoleDto;
import ru.smaginv.debtmanager.web.dto.user.UserStatusDto;

import java.util.List;

public interface UserService {

    UserDto get(Long userId);

    UserDto getByUsername(String username);

    UserDto getByEmail(UserEmailDto userEmailDto);

    List<UserDto> getAll();

    List<UserDto> getAllByStatus(UserStatusDto userStatusDto);

    void update(UserDto userDto);

    UserDto setStatus(Long userId, UserStatusDto userStatusDto);

    UserDto setRole(Long userId, UserRoleDto userRoleDto);

    UserDto create(UserDto userDto);

    void delete(Long userId);

    void deleteByEmail(UserEmailDto userEmailDto);
}
