package ru.smaginv.debtmanager.lm.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.smaginv.debtmanager.lm.entity.user.User;
import ru.smaginv.debtmanager.lm.repository.user.UserRepository;
import ru.smaginv.debtmanager.lm.util.ValidationUtil;
import ru.smaginv.debtmanager.lm.util.exception.NotFoundException;
import ru.smaginv.debtmanager.lm.web.dto.user.UserDto;
import ru.smaginv.debtmanager.lm.web.dto.user.UserEmailDto;
import ru.smaginv.debtmanager.lm.web.dto.user.UsernameDto;
import ru.smaginv.debtmanager.lm.web.mapping.UserMapper;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper,
                           ValidationUtil validationUtil) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public UserDto getByUsername(String username) {
        User user = userRepository.getByUsername(username).orElseThrow(
                () -> new NotFoundException("User '" + username + "' was not found")
        );
        return userMapper.mapDto(user);
    }

    @Override
    public UserDto getByUsername(UsernameDto usernameDto) {
        return getByUsername(usernameDto.getUsername());
    }

    @Override
    public UserDto getByEmail(UserEmailDto userEmailDto) {
        User user = userRepository.getByEmail(userEmailDto.getEmail()).orElseThrow(
                () -> new NotFoundException("User with email: '" + userEmailDto.getEmail() + "' was not found")
        );
        return userMapper.mapDto(user);
    }

    @Override
    public List<UserDto> getAll() {
        return userMapper.mapDtos(userRepository.getAll());
    }

    @Transactional
    @Override
    public UserDto create(UserDto userDto) {
        User user = userMapper.map(userDto);
        return userMapper.mapDto(userRepository.save(user));
    }

    @Transactional
    @Override
    public void deleteByUsername(UsernameDto usernameDto) {
        validationUtil.checkNotFound(userRepository.deleteByUsername(usernameDto.getUsername()) != 0);
    }

    @Transactional
    @Override
    public void deleteByEmail(UserEmailDto userEmailDto) {
        validationUtil.checkNotFound(userRepository.deleteByEmail(userEmailDto.getEmail()) != 0);
    }
}
