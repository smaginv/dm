package ru.smaginv.debtmanager.web.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.smaginv.debtmanager.service.user.UserService;
import ru.smaginv.debtmanager.util.validation.ValidationUtil;
import ru.smaginv.debtmanager.web.dto.user.UserDto;
import ru.smaginv.debtmanager.web.dto.user.UserEmailDto;

import javax.validation.Valid;
import java.net.URI;

@Log4j2
@RestController
@RequestMapping(
        value = "/user",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class ProfileController {

    private final UserService userService;
    private final ValidationUtil validationUtil;

    @Autowired
    public ProfileController(UserService userService, ValidationUtil validationUtil) {
        this.userService = userService;
        this.validationUtil = validationUtil;
    }

    @GetMapping(
            value = "/{userId}",
            consumes = MediaType.ALL_VALUE
    )
    public ResponseEntity<UserDto> get(@PathVariable Long userId) {
        log.info("get user by id: {}", userId);
        return ResponseEntity.ok(userService.get(userId));
    }

    @GetMapping(
            value = "/by-email"
    )
    public ResponseEntity<UserDto> getByEmail(@Valid @RequestBody UserEmailDto userEmailDto) {
        log.info("get user by email: {}", userEmailDto);
        return ResponseEntity.ok(userService.getByEmail(userEmailDto));
    }

    @PutMapping(
            value = "/{userId}"
    )
    public ResponseEntity<?> update(@PathVariable Long userId, @Valid @RequestBody UserDto userDto) {
        validationUtil.assureIdConsistent(userDto, String.valueOf(userId));
        log.info("update user: {}", userDto);
        userService.update(userDto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(
            value = "/register"
    )
    public ResponseEntity<UserDto> register(@Valid @RequestBody UserDto userDto) {
        validationUtil.checkIsNew(userDto);
        log.info("register user: {}", userDto);
        UserDto created = userService.create(userDto);
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/user/{userId}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @DeleteMapping(
            value = "/{userId}",
            consumes = MediaType.ALL_VALUE
    )
    public ResponseEntity<?> delete(@PathVariable Long userId) {
        log.info("delete user by id: {}", userId);
        userService.delete(userId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(
            value = "/by-email"
    )
    public ResponseEntity<?> deleteByEmail(@Valid @RequestBody UserEmailDto userEmailDto) {
        log.info("delete user by email: {}", userEmailDto);
        userService.deleteByEmail(userEmailDto);
        return ResponseEntity.noContent().build();
    }
}
