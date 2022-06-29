package ru.smaginv.debtmanager.dm.web.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.smaginv.debtmanager.dm.message.Message;
import ru.smaginv.debtmanager.dm.message.MessageService;
import ru.smaginv.debtmanager.dm.service.user.UserService;
import ru.smaginv.debtmanager.dm.web.AuthUser;
import ru.smaginv.debtmanager.dm.web.dto.user.*;

import javax.servlet.http.HttpServletRequest;
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
    private final MessageService messageService;

    @Autowired
    public AdminController(UserService userService, PasswordEncoder passwordEncoder,
                           MessageService messageService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.messageService = messageService;
    }

    @GetMapping
    public ResponseEntity<UserDto> get(@AuthenticationPrincipal AuthUser authUser,
                                       @Valid @RequestBody UserIdDto userIdDto,
                                       HttpServletRequest request) {
        log.info("get user: {}", userIdDto);
        UserDto user = userService.get(userIdDto);
        Message message = messageService.createMessage(authUser.getUsername(), request.getRequestURI(),
                request.getMethod(), userIdDto, user);
        messageService.sendMessage(message);
        return ResponseEntity.ok(user);
    }

    @GetMapping(
            value = "/by-username"
    )
    public ResponseEntity<UserDto> getByUsername(@AuthenticationPrincipal AuthUser authUser,
                                                 @Valid @RequestBody UsernameDto usernameDto,
                                                 HttpServletRequest request) {
        log.info("get user by username: {}", usernameDto);
        UserDto user = userService.getByUsername(usernameDto);
        Message message = messageService.createMessage(authUser.getUsername(), request.getRequestURI(),
                request.getMethod(), usernameDto, user);
        messageService.sendMessage(message);
        return ResponseEntity.ok(user);
    }

    @GetMapping(
            value = "/by-email"
    )
    public ResponseEntity<UserDto> getByEmail(@AuthenticationPrincipal AuthUser authUser,
                                              @Valid @RequestBody UserEmailDto userEmailDto,
                                              HttpServletRequest request) {
        log.info("get user by email: {}", userEmailDto);
        UserDto user = userService.getByEmail(userEmailDto);
        Message message = messageService.createMessage(authUser.getUsername(), request.getRequestURI(),
                request.getMethod(), userEmailDto, user);
        messageService.sendMessage(message);
        return ResponseEntity.ok(user);
    }

    @GetMapping(
            consumes = MediaType.ALL_VALUE
    )
    public ResponseEntity<List<UserDto>> getAll(@AuthenticationPrincipal AuthUser authUser,
                                                HttpServletRequest request) {
        log.info("get all users");
        List<UserDto> users = userService.getAll();
        Message message = messageService.createMessage(authUser.getUsername(), request.getRequestURI(),
                request.getMethod(), RequestEntity.EMPTY, users);
        messageService.sendMessage(message);
        return ResponseEntity.ok(users);
    }

    @GetMapping(
            value = "/by-status"
    )
    public ResponseEntity<List<UserDto>> getAllByStatus(@AuthenticationPrincipal AuthUser authUser,
                                                        @Valid @RequestBody UserStatusDto userStatusDto,
                                                        HttpServletRequest request) {
        log.info("get all by state: {}", userStatusDto);
        List<UserDto> users = userService.getAllByStatus(userStatusDto);
        Message message = messageService.createMessage(authUser.getUsername(), request.getRequestURI(),
                request.getMethod(), userStatusDto, users);
        messageService.sendMessage(message);
        return ResponseEntity.ok(users);
    }

    @PutMapping
    public ResponseEntity<?> update(@AuthenticationPrincipal AuthUser authUser,
                                    @Valid @RequestBody UserUpdateDto userUpdateDto,
                                    HttpServletRequest request) {
        log.info("update user: {}", userUpdateDto);
        String encodePassword = passwordEncoder.encode(userUpdateDto.getPassword());
        userUpdateDto.setPassword(encodePassword);
        userService.update(userUpdateDto);
        Message message = messageService.createMessage(authUser.getUsername(), request.getRequestURI(),
                request.getMethod(), userUpdateDto, ResponseEntity.EMPTY);
        messageService.sendMessage(message);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(
            value = "/set-status"
    )
    public ResponseEntity<UserDto> setStatus(@AuthenticationPrincipal AuthUser authUser,
                                             @Valid @RequestBody UserStatusDto userStatusDto,
                                             HttpServletRequest request) {
        log.info("set status: {}, for user: {}", userStatusDto.getStatus(), userStatusDto.getUserId());
        UserDto user = userService.setStatus(userStatusDto);
        Message message = messageService.createMessage(authUser.getUsername(), request.getRequestURI(),
                request.getMethod(), userStatusDto, user);
        messageService.sendMessage(message);
        return ResponseEntity.ok(user);
    }

    @PatchMapping(
            value = "/set-role"
    )
    public ResponseEntity<UserDto> setRole(@AuthenticationPrincipal AuthUser authUser,
                                           @Valid @RequestBody UserRoleDto userRoleDto,
                                           HttpServletRequest request) {
        log.info("set role: {}, for user: {}", userRoleDto.getRole(), userRoleDto.getUserId());
        UserDto user = userService.setRole(userRoleDto);
        Message message = messageService.createMessage(authUser.getUsername(), request.getRequestURI(),
                request.getMethod(), userRoleDto, user);
        messageService.sendMessage(message);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<UserDto> create(@AuthenticationPrincipal AuthUser authUser,
                                          @Valid @RequestBody UserDto userDto,
                                          HttpServletRequest request) {
        log.info("create user: {}", userDto);
        String encodePassword = passwordEncoder.encode(userDto.getPassword());
        userDto.setPassword(encodePassword);
        UserDto created = userService.create(userDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .build()
                .toUri();
        Message message = messageService.createMessage(authUser.getUsername(), request.getRequestURI(),
                request.getMethod(), userDto, created);
        messageService.sendMessage(message);
        return ResponseEntity.created(location).body(created);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteById(@AuthenticationPrincipal AuthUser authUser,
                                        @Valid @RequestBody UserIdDto userIdDto,
                                        HttpServletRequest request) {
        log.info("delete user by id: {}", userIdDto);
        userService.delete(userIdDto);
        Message message = messageService.createMessage(authUser.getUsername(), request.getRequestURI(),
                request.getMethod(), userIdDto, ResponseEntity.EMPTY);
        messageService.sendMessage(message);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(
            value = "/by-email"
    )
    public ResponseEntity<?> deleteByEmail(@AuthenticationPrincipal AuthUser authUser,
                                           @Valid @RequestBody UserEmailDto userEmailDto,
                                           HttpServletRequest request) {
        log.info("delete user by email: {}", userEmailDto);
        userService.deleteByEmail(userEmailDto);
        Message message = messageService.createMessage(authUser.getUsername(), request.getRequestURI(),
                request.getMethod(), userEmailDto, ResponseEntity.EMPTY);
        messageService.sendMessage(message);
        return ResponseEntity.noContent().build();
    }
}
