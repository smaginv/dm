package ru.smaginv.debtmanager.dm.entity.operation;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum OperationType {

    RECEIPT("RECEIPT"),
    EXPENSE("EXPENSE");

    private final String value;

    OperationType(String value) {
        this.value = value;
    }

    private static final Map<String, OperationType> OPERATION_TYPES;

    static {
        Map<String, OperationType> types = new HashMap<>();
        Arrays.stream(OperationType.values())
                .forEach(type -> types.put(type.value, type));
        OPERATION_TYPES = Map.copyOf(types);
    }

    public static OperationType getByValue(String value) {
        return OPERATION_TYPES.get(value);
    }
}
