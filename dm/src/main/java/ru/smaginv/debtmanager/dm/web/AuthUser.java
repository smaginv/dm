package ru.smaginv.debtmanager.dm.web;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import ru.smaginv.debtmanager.dm.web.dto.user.UserDto;

import java.util.stream.Collectors;

import static ru.smaginv.debtmanager.dm.util.AppUtil.ROLE_PREFIX;

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
