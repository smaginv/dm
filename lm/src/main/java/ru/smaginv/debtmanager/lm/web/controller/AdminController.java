package ru.smaginv.debtmanager.lm.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.smaginv.debtmanager.lm.service.user.UserService;
import ru.smaginv.debtmanager.lm.web.dto.user.UserDto;
import ru.smaginv.debtmanager.lm.web.dto.user.UserEmailDto;
import ru.smaginv.debtmanager.lm.web.dto.user.UsernameDto;

import javax.validation.Valid;
import java.util.List;

@Log4j2
@RestController
@RequestMapping(
        value = "/admin/users",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
@Tag(name = "Administrator controller")
public class AdminController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(
            value = "/by-username/get"
    )
    @Operation(summary = "Get user by username")
    public ResponseEntity<UserDto> getByUsername(@Valid @RequestBody UsernameDto usernameDto) {
        log.info("get user by username: {}", usernameDto);
        return ResponseEntity.ok(userService.getByUsername(usernameDto));
    }

    @PostMapping(
            value = "/by-email/get"
    )
    @Operation(summary = "Get user by email")
    public ResponseEntity<UserDto> getByEmail(@Valid @RequestBody UserEmailDto userEmailDto) {
        log.info("get user by email: {}", userEmailDto);
        return ResponseEntity.ok(userService.getByEmail(userEmailDto));
    }

    @GetMapping(
            consumes = MediaType.ALL_VALUE
    )
    @Operation(summary = "Get all users")
    public ResponseEntity<List<UserDto>> getAll() {
        log.info("get all users");
        return ResponseEntity.ok(userService.getAll());
    }

    @PostMapping
    @Operation(summary = "Create user")
    public ResponseEntity<UserDto> create(@Valid @RequestBody UserDto userDto) {
        log.info("create user: {}", userDto);
        String encodePassword = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encodePassword);
        UserDto created = userService.create(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PostMapping(
            value = "/by-username/delete"
    )
    @Operation(summary = "Delete user by username")
    public ResponseEntity<?> deleteByUsername(@Valid @RequestBody UsernameDto usernameDto) {
        log.info("delete user by username: {}", usernameDto.getUsername());
        userService.deleteByUsername(usernameDto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(
            value = "/by-email/delete"
    )
    @Operation(summary = "Delete user by email")
    public ResponseEntity<?> deleteByEmail(@Valid @RequestBody UserEmailDto userEmailDto) {
        log.info("delete user by email: {}", userEmailDto);
        userService.deleteByEmail(userEmailDto);
        return ResponseEntity.noContent().build();
    }
}
