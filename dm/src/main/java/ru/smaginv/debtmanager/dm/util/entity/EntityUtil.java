package ru.smaginv.debtmanager.dm.util.entity;

import ru.smaginv.debtmanager.dm.util.exception.NotFoundException;

import java.util.Optional;

public class EntityUtil {

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static <T> T getEntityFromOptional(Optional<T> entity) {
        return entity.orElseThrow(() ->
                new NotFoundException("Not found entity"));
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static <T, ID> T getEntityFromOptional(Optional<T> entity, ID id) {
        return entity.orElseThrow(() ->
                new NotFoundException("Not found entity with id: " + id));
    }
}
