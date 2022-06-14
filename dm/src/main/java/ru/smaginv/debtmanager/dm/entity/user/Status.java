package ru.smaginv.debtmanager.dm.entity.user;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum Status {

    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE");

    private final String value;

    Status(String value) {
        this.value = value;
    }

    private static final Map<String, Status> USER_STATUSES;

    static {
        Map<String, Status> statuses = new HashMap<>();
        Arrays.stream(Status.values())
                .forEach(status -> statuses.put(status.value, status));
        USER_STATUSES = Map.copyOf(statuses);
    }

    public static Status getByValue(String value) {
        return USER_STATUSES.get(value);
    }
}
