package ru.smaginv.debtmanager.entity.account;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum AccountStatus {

    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE"),
    RESUMED("RESUMED");

    private final String value;

    AccountStatus(String value) {
        this.value = value;
    }

    private static final Map<String, AccountStatus> ACCOUNT_STATUSES;

    static {
        Map<String, AccountStatus> statuses = new HashMap<>();
        Arrays.stream(AccountStatus.values())
                .forEach(status -> statuses.put(status.value, status));
        ACCOUNT_STATUSES = Map.copyOf(statuses);
    }

    public static AccountStatus getByValue(String value) {
        return ACCOUNT_STATUSES.get(value);
    }
}
