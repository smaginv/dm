package ru.smaginv.debtmanager.util;

import ru.smaginv.debtmanager.entity.HasId;

import javax.persistence.EntityNotFoundException;

public class ValidationUtil {

    public static void checkNotFound(boolean found) {
        if (!found) {
            throw new EntityNotFoundException();
        }
    }

    public static void checkNotFoundWithId(boolean found, Long id) {
        if (!found) {
            throw new EntityNotFoundException("not found with id: " + id);
        }
    }

    public static <T> void checkIsNew(HasId<T> entity) {
        if (!entity.isNew()) {
            throw new IllegalArgumentException(entity + " must be new (id=null)");
        }
    }
}
