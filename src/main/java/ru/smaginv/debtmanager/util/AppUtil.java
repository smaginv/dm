package ru.smaginv.debtmanager.util;

import org.springframework.security.core.context.SecurityContextHolder;
import ru.smaginv.debtmanager.entity.user.Role;
import ru.smaginv.debtmanager.web.AuthUser;

import java.time.LocalDateTime;
import java.util.Objects;

public class AppUtil {

    public static final String ROLE_PREFIX = "ROLE_";

    public static final String ADMIN = Role.ADMIN.name();

    public static final String USER = Role.USER.name();

    public static final String VALIDATION_FAILED = "VALIDATION FAILED";

    public static final LocalDateTime MIN_DATE = LocalDateTime.of(2000, 1, 1, 0, 0, 0);

    public static Long getAuthUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AuthUser authUser = null;
        if (principal instanceof AuthUser)
            authUser = (AuthUser) principal;
        return Objects.isNull(authUser) ? null : authUser.getId();
    }
}
