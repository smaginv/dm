package ru.smaginv.debtmanager.dm.entity.account;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum AccountType {

    LEND("LEND"),
    LOAN("LOAN");

    private final String value;

    AccountType(String value) {
        this.value = value;
    }

    private static final Map<String, AccountType> ACCOUNT_TYPES;

    static {
        Map<String, AccountType> types = new HashMap<>();
        Arrays.stream(AccountType.values())
                .forEach(type -> types.put(type.value, type));
        ACCOUNT_TYPES = Map.copyOf(types);
    }

    public static AccountType getByValue(String value) {
        return ACCOUNT_TYPES.get(value);
    }
}
