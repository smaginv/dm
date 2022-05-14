package ru.smaginv.debtmanager.util;

import ru.smaginv.debtmanager.web.dto.HasIdDto;

public class MappingUtil {

    public static Long map(HasIdDto entity) {
        return Long.valueOf(entity.getId().trim());
    }
}
