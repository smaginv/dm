package ru.smaginv.debtmanager.service.user;

import ru.smaginv.debtmanager.web.dto.user.UserDto;
import ru.smaginv.debtmanager.web.dto.user.UserEmailDto;

import java.util.List;

public interface UserService {

    UserDto get(Long userId);

    UserDto getByEmail(UserEmailDto userEmailDto);

    List<UserDto> getAll();

    void update(UserDto userDto);

    UserDto create(UserDto userDto);

    void delete(Long userId);

    void deleteByEmail(UserEmailDto userEmailDto);
}
