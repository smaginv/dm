package ru.smaginv.debtmanager.util;

import ru.smaginv.debtmanager.web.dto.person.PersonIdDto;

public class MappingUtil {

    public static Long map(PersonIdDto personIdDto) {
        return Long.valueOf(personIdDto.getId().trim());
    }
}
