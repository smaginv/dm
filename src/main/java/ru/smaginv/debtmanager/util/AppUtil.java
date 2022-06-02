package ru.smaginv.debtmanager.util;

import ru.smaginv.debtmanager.entity.user.Role;

import java.time.LocalDateTime;

public class AppUtil {

    public static final String ROLE_PREFIX = "ROLE_";

    public static final String ADMIN = Role.ADMIN.name();

    public static final String USER = Role.USER.name();

    public static final String VALIDATION_FAILED = "VALIDATION FAILED";

    public static final LocalDateTime MIN_DATE = LocalDateTime.of(2000, 1, 1, 0, 0, 0);
}
