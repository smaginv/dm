package ru.smaginv.debtmanager.dm.web.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.smaginv.debtmanager.dm.service.user.UserService;
import ru.smaginv.debtmanager.dm.web.dto.user.*;

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
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public ResponseEntity<UserDto> get(@Valid @RequestBody UserIdDto userIdDto) {
        log.info("get user: {}", userIdDto);
        return ResponseEntity.ok(userService.get(userIdDto));
    }

    @GetMapping(
            value = "/by-username"
    )
    public ResponseEntity<UserDto> getByUsername(@Valid @RequestBody UsernameDto usernameDto) {
        log.info("get user by username: {}", usernameDto);
        return ResponseEntity.ok(userService.getByUsername(usernameDto));
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

    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody UserUpdateDto userUpdateDto) {
        log.info("update user: {}", userUpdateDto);
        String encodePassword = passwordEncoder.encode(userUpdateDto.getPassword());
        userUpdateDto.setPassword(encodePassword);
        userService.update(userUpdateDto);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(
            value = "/set-status"
    )
    public ResponseEntity<UserDto> setStatus(@Valid @RequestBody UserStatusDto userStatusDto) {
        log.info("set status: {}, for user: {}", userStatusDto.getStatus(), userStatusDto.getUserId());
        return ResponseEntity.ok(userService.setStatus(userStatusDto));
    }

    @PatchMapping(
            value = "/set-role"
    )
    public ResponseEntity<UserDto> setRole(@Valid @RequestBody UserRoleDto userRoleDto) {
        log.info("set role: {}, for user: {}", userRoleDto.getRole(), userRoleDto.getUserId());
        return ResponseEntity.ok(userService.setRole(userRoleDto));
    }

    @PostMapping
    public ResponseEntity<UserDto> create(@Valid @RequestBody UserDto userDto) {
        log.info("create user: {}", userDto);
        String encodePassword = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encodePassword);
        UserDto created = userService.create(userDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .build()
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteById(@Valid @RequestBody UserIdDto userIdDto) {
        log.info("delete user by id: {}", userIdDto);
        userService.delete(userIdDto);
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
