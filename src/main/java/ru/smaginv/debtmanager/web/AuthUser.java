package ru.smaginv.debtmanager.web;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import ru.smaginv.debtmanager.web.dto.user.UserDto;

import java.util.stream.Collectors;

import static ru.smaginv.debtmanager.util.AppUtil.ROLE_PREFIX;

@Getter
public class AuthUser extends User {

    private final Long id;

    public AuthUser(UserDto userDto) {
        super(userDto.getEmail(),
                userDto.getPassword(),
                userDto.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(ROLE_PREFIX + role))
                        .collect(Collectors.toSet()));
        this.id = Long.parseLong(userDto.getId());
    }
}
