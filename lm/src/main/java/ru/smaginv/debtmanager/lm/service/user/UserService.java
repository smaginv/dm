package ru.smaginv.debtmanager.lm.service.user;

import ru.smaginv.debtmanager.lm.web.dto.user.UserDto;
import ru.smaginv.debtmanager.lm.web.dto.user.UserEmailDto;
import ru.smaginv.debtmanager.lm.web.dto.user.UsernameDto;

import java.util.List;

public interface UserService {

    UserDto getByUsername(String username);

    UserDto getByUsername(UsernameDto usernameDto);

    UserDto getByEmail(UserEmailDto userEmailDto);

    List<UserDto> getAll();

    UserDto create(UserDto userDto);

    void deleteByUsername(UsernameDto usernameDto);

    void deleteByEmail(UserEmailDto userEmailDto);
}
