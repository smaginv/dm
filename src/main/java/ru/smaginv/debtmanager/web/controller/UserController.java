package ru.smaginv.debtmanager.web.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.smaginv.debtmanager.service.user.UserService;
import ru.smaginv.debtmanager.util.validation.ValidationUtil;
import ru.smaginv.debtmanager.web.AuthUser;
import ru.smaginv.debtmanager.web.dto.user.UserDto;

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

    @GetMapping(
            consumes = MediaType.ALL_VALUE
    )
    public ResponseEntity<UserDto> get(@AuthenticationPrincipal AuthUser authUser) {
        log.info("get user : {}", authUser);
        return ResponseEntity.ok(userService.get(authUser.getId()));
    }

    @PutMapping
    public ResponseEntity<?> update(@AuthenticationPrincipal AuthUser authUser,
                                    @Valid @RequestBody UserDto userDto) {
        validationUtil.assureIdConsistent(userDto, authUser.getId());
        log.info("update user: {}", userDto);
        String encodePassword = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encodePassword);
        userService.update(userDto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(
            value = "/register"
    )
    public ResponseEntity<UserDto> register(@Valid @RequestBody UserDto userDto) {
        validationUtil.checkIsNew(userDto);
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

    @DeleteMapping(
            consumes = MediaType.ALL_VALUE
    )
    public ResponseEntity<?> delete(@AuthenticationPrincipal AuthUser authUser) {
        log.info("delete user: {}", authUser);
        userService.delete(authUser.getId());
        return ResponseEntity.noContent().build();
    }
}
