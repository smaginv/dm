package ru.smaginv.debtmanager.dm.web.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.smaginv.debtmanager.dm.service.user.UserService;
import ru.smaginv.debtmanager.dm.util.validation.ValidationUtil;
import ru.smaginv.debtmanager.dm.web.AuthUser;
import ru.smaginv.debtmanager.dm.web.dto.user.UserDto;
import ru.smaginv.debtmanager.dm.web.dto.user.UserIdDto;
import ru.smaginv.debtmanager.dm.web.dto.user.UserUpdateDto;

import javax.validation.Valid;
import java.net.URI;

@Log4j2
@RestController
@RequestMapping(
        value = "/user",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class UserController {

    private final UserService userService;
    private final ValidationUtil validationUtil;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, ValidationUtil validationUtil,
                          PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.validationUtil = validationUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public ResponseEntity<UserDto> get(@AuthenticationPrincipal AuthUser authUser,
                                       @Valid @RequestBody UserIdDto userIdDto) {
        validationUtil.checkIdEquality(authUser.getId(), userIdDto);
        log.info("get user : {}", authUser);
        return ResponseEntity.ok(userService.get(userIdDto));
    }

    @PutMapping
    public ResponseEntity<?> update(@AuthenticationPrincipal AuthUser authUser,
                                    @Valid @RequestBody UserUpdateDto userUpdateDto) {
        validationUtil.checkIdEquality(authUser.getId(), userUpdateDto);
        log.info("update user: {}", userUpdateDto);
        String encodePassword = passwordEncoder.encode(userUpdateDto.getPassword());
        userUpdateDto.setPassword(encodePassword);
        userService.update(userUpdateDto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(
            value = "/register"
    )
    public ResponseEntity<UserDto> register(@Valid @RequestBody UserDto userDto) {
        log.info("register user: {}", userDto);
        String encodePassword = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encodePassword);
        UserDto created = userService.create(userDto);
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/user/{userId}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@AuthenticationPrincipal AuthUser authUser,
                                    @Valid @RequestBody UserIdDto userIdDto) {
        validationUtil.checkIdEquality(authUser.getId(), userIdDto);
        log.info("delete user: {}", authUser);
        userService.delete(userIdDto);
        return ResponseEntity.noContent().build();
    }
}
