package ru.smaginv.debtmanager.lm.web;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import ru.smaginv.debtmanager.lm.web.dto.user.UserDto;

import java.util.List;

import static ru.smaginv.debtmanager.lm.util.AppUtil.ROLE_PREFIX;

@Getter
public class AuthUser extends User {

    private final Long id;

    public AuthUser(UserDto userDto) {
        super(userDto.getEmail(),
                userDto.getPassword(),
                List.of(new SimpleGrantedAuthority(ROLE_PREFIX + userDto.getRole())));
        this.id = Long.parseLong(userDto.getId());
    }
}
