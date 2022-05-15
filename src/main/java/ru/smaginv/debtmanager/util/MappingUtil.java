package ru.smaginv.debtmanager.util;

import ru.smaginv.debtmanager.web.dto.HasIdDto;

public class MappingUtil {

    public static Long mapId(HasIdDto entity) {
        return Long.valueOf(entity.getId().trim());
    }
}
