package ru.smaginv.debtmanager.dm.entity.contact;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum ContactType {

    PHONE("PHONE"),
    EMAIL("EMAIL");

    private final String value;

    ContactType(String value) {
        this.value = value;
    }

    private static final Map<String, ContactType> CONTACT_TYPES;

    static {
        Map<String, ContactType> types = new HashMap<>();
        Arrays.stream(ContactType.values())
                .forEach(type -> types.put(type.value, type));
        CONTACT_TYPES = Map.copyOf(types);
    }

    public static ContactType getByValue(String value) {
        return CONTACT_TYPES.get(value);
    }
}
