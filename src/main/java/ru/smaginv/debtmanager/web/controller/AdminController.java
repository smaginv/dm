package ru.smaginv.debtmanager.web.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.smaginv.debtmanager.service.user.UserService;
import ru.smaginv.debtmanager.util.validation.ValidationUtil;
import ru.smaginv.debtmanager.web.dto.user.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Log4j2
@RestController
@RequestMapping(
        value = "/admin/users",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class AdminController {

    private final UserService userService;
    private final ValidationUtil validationUtil;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(UserService userService, ValidationUtil validationUtil,
                           PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.validationUtil = validationUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping(
            value = "/{userId}",
            consumes = MediaType.ALL_VALUE
    )
    public ResponseEntity<UserDto> getById(@PathVariable Long userId) {
        log.info("get user by id: {}", userId);
        return ResponseEntity.ok(userService.get(userId));
    }

    @GetMapping(
            value = "/by-username"
    )
    public ResponseEntity<UserDto> getByUsername(@Valid @RequestBody UsernameDto usernameDto) {
        log.info("get user by username: {}", usernameDto);
        return ResponseEntity.ok(userService.getByUsername(usernameDto.getUsername()));
    }

    @GetMapping(
            value = "/by-email"
    )
    public ResponseEntity<UserDto> getByEmail(@Valid @RequestBody UserEmailDto userEmailDto) {
        log.info("get user by email: {}", userEmailDto);
        return ResponseEntity.ok(userService.getByEmail(userEmailDto));
    }

    @GetMapping(
            consumes = MediaType.ALL_VALUE
    )
    public ResponseEntity<List<UserDto>> getAll() {
        log.info("get all users");
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping(
            value = "/by-status"
    )
    public ResponseEntity<List<UserDto>> getAllByStatus(@Valid @RequestBody UserStatusDto userStatusDto) {
        log.info("get all by state: {}", userStatusDto);
        return ResponseEntity.ok(userService.getAllByStatus(userStatusDto));
    }

    @PutMapping(
            value = "/{userId}"
    )
    public ResponseEntity<?> update(@PathVariable Long userId, @Valid @RequestBody UserDto userDto) {
        validationUtil.assureIdConsistent(userDto, userId);
        log.info("update user: {}", userDto);
        String encodePassword = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encodePassword);
        userService.update(userDto);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(
            value = "/{userId}/set-status"
    )
    public ResponseEntity<UserDto> setStatus(@PathVariable Long userId,
                                             @Valid @RequestBody UserStatusDto userStatusDto) {
        log.info("set status: {}, for user: {}", userStatusDto, userId);
        return ResponseEntity.ok(userService.setStatus(userId, userStatusDto));
    }

    @PatchMapping(
            value = "/{userId}/set-role"
    )
    public ResponseEntity<UserDto> setRole(@PathVariable Long userId,
                                           @Valid @RequestBody UserRoleDto userRoleDto) {
        log.info("set role: {}, for user: {}", userRoleDto, userId);
        return ResponseEntity.ok(userService.setRole(userId, userRoleDto));
    }

    @PostMapping
    public ResponseEntity<UserDto> create(@Valid @RequestBody UserDto userDto) {
        validationUtil.checkIsNew(userDto);
        log.info("create user: {}", userDto);
        String encodePassword = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encodePassword);
        UserDto created = userService.create(userDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{userId}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @DeleteMapping(
            value = "/{userId}",
            consumes = MediaType.ALL_VALUE
    )
    public ResponseEntity<?> deleteById(@PathVariable Long userId) {
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
