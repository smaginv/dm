package ru.smaginv.debtmanager.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.smaginv.debtmanager.entity.user.Role;
import ru.smaginv.debtmanager.entity.user.Status;
import ru.smaginv.debtmanager.entity.user.User;
import ru.smaginv.debtmanager.repository.user.UserRepository;
import ru.smaginv.debtmanager.util.MappingUtil;
import ru.smaginv.debtmanager.util.exception.EntityStatusException;
import ru.smaginv.debtmanager.util.exception.NotFoundException;
import ru.smaginv.debtmanager.util.validation.ValidationUtil;
import ru.smaginv.debtmanager.web.dto.user.*;
import ru.smaginv.debtmanager.web.mapping.UserMapper;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static ru.smaginv.debtmanager.util.entity.EntityUtil.getEntityFromOptional;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final MappingUtil mappingUtil;
    private final ValidationUtil validationUtil;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper,
                           MappingUtil mappingUtil, ValidationUtil validationUtil) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.mappingUtil = mappingUtil;
        this.validationUtil = validationUtil;
    }

    @Override
    public UserDto get(UserIdDto userIdDto) {
        return userMapper.mapDto(getUser(mappingUtil.mapId(userIdDto)));
    }

    @Override
    public UserDto getByUsername(String username) {
        User user = userRepository.getByUsername(username).orElseThrow();
        return userMapper.mapDto(user);
    }

    @Override
    public UserDto getByUsername(UsernameDto usernameDto) {
        String username = usernameDto.getUsername();
        User user = userRepository.getByUsername(username).orElseThrow(
                () -> new NotFoundException("User '" + username + "' was not found")
        );
        return userMapper.mapDto(user);
    }

    @Override
    public UserDto getByEmail(UserEmailDto userEmailDto) {
        User user = getEntityFromOptional(userRepository.getByEmail(userEmailDto.getEmail()));
        return userMapper.mapDto(user);
    }

    @Override
    public List<UserDto> getAll() {
        return userMapper.mapDtos(userRepository.getAll());
    }

    @Override
    public List<UserDto> getAllByStatus(UserStatusDto userStatusDto) {
        Status status = Status.getByValue(userStatusDto.getStatus());
        return userMapper.mapDtos(userRepository.getAllByStatus(status));
    }

    @Transactional
    @Override
    public void update(UserUpdateDto userUpdateDto) {
        User user = getUser(mappingUtil.mapId(userUpdateDto));
        userMapper.update(userUpdateDto, user);
        userRepository.save(user);
    }

    @Transactional
    @Override
    public UserDto setStatus(UserStatusDto userStatusDto) {
        Long userId = mapUserId(userStatusDto.getUserId());
        User user = getUser(userId);
        Status newValue = Status.getByValue(userStatusDto.getStatus());
        checkCompliance(user.getStatus(), newValue);
        user.setStatus(newValue);
        return userMapper.mapDto(userRepository.save(user));
    }

    @Transactional
    @Override
    public UserDto setRole(UserRoleDto userRoleDto) {
        Long userId = mapUserId(userRoleDto.getUserId());
        User user = getUser(userId);
        if (user.getStatus().equals(Status.INACTIVE))
            throw new EntityStatusException("status of user must be: " + Status.ACTIVE);
        Role newValue = Role.getByValue(userRoleDto.getRole());
        user.getRoles().forEach(role -> checkCompliance(role, newValue));
        user.getRoles().add(newValue);
        return userMapper.mapDto(userRepository.save(user));
    }

    @Transactional
    @Override
    public UserDto create(UserDto userDto) {
        User user = userMapper.map(userDto);
        user.setStatus(Status.ACTIVE);
        user.setRoles(Set.of(Role.USER));
        user = userRepository.save(user);
        return userMapper.mapDto(user);
    }

    @Transactional
    @Override
    public void delete(UserIdDto userIdDto) {
        Long userId = mappingUtil.mapId(userIdDto);
        validationUtil.checkNotFoundWithId(userRepository.deleteByEmail(userId) != 0, userId);
    }

    @Transactional
    @Override
    public void deleteByEmail(UserEmailDto userEmailDto) {
        validationUtil.checkNotFound(userRepository.deleteByEmail(userEmailDto.getEmail()) != 0);
    }

    private Long mapUserId(String userId) {
        if (Objects.isNull(userId))
            throw new ValidationException("userId must be non null");
        return mappingUtil.mapId(userId);
    }

    private User getUser(Long userId) {
        return getEntityFromOptional(userRepository.get(userId), userId);
    }

    private <T extends Enum<?>> void checkCompliance(T oldValue, T newValue) {
        if (oldValue.equals(newValue))
            throw new IllegalArgumentException("the user already has the value: " + oldValue);
    }
}
