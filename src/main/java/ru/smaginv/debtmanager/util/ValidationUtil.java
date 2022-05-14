package ru.smaginv.debtmanager.util;

import ru.smaginv.debtmanager.web.dto.HasIdDto;

import javax.persistence.EntityNotFoundException;

public class ValidationUtil {

    public static void checkNotFound(boolean found) {
        if (!found) {
            throw new EntityNotFoundException();
        }
    }

    public static void checkNotFoundWithId(boolean found, HasIdDto entity) {
        if (!found) {
            throw new EntityNotFoundException("not found with id: " + entity.getId());
        }
    }

    public static void checkIsNew(HasIdDto entity) {
        if (!entity.isNew()) {
            throw new IllegalArgumentException(entity + " must be new (id=null)");
        }
    }
}
