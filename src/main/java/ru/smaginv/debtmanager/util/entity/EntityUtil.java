package ru.smaginv.debtmanager.util.entity;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

public class EntityUtil {

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static <T> T getEntityFromOptional(Optional<T> entity) {
        return entity.orElseThrow(() ->
                new EntityNotFoundException("Not found entity"));
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static <T, ID> T getEntityFromOptional(Optional<T> entity, ID id) {
        return entity.orElseThrow(() ->
                new EntityNotFoundException("Not found entity with id: " + id));
    }
}
