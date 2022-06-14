package ru.smaginv.debtmanager.dm.entity.user;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum Role {

    ADMIN("ADMIN"),
    USER("USER");

    private final String value;

    Role(String value) {
        this.value = value;
    }

    private static final Map<String, Role> USER_ROLES;

    static {
        Map<String, Role> roles = new HashMap<>();
        Arrays.stream(Role.values())
                .forEach(role -> roles.put(role.value, role));
        USER_ROLES = Map.copyOf(roles);
    }

    public static Role getByValue(String value) {
        return USER_ROLES.get(value);
    }
}
