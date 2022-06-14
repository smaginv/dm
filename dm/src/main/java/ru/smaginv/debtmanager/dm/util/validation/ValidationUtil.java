package ru.smaginv.debtmanager.dm.util.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import ru.smaginv.debtmanager.dm.config.PropertiesConfig;
import ru.smaginv.debtmanager.dm.entity.contact.ContactType;
import ru.smaginv.debtmanager.dm.util.exception.NotFoundException;
import ru.smaginv.debtmanager.dm.web.dto.HasIdDto;
import ru.smaginv.debtmanager.dm.web.dto.contact.AbstractContactDto;

import javax.validation.ValidationException;
import java.util.List;

@Component
public class ValidationUtil {

    private final List<String> phonePatterns;
    private final List<String> emailPatterns;

    @Autowired
    public ValidationUtil(PropertiesConfig propertiesConfig) {
        this.phonePatterns = propertiesConfig.pattern().getPhones();
        this.emailPatterns = propertiesConfig.pattern().getEmails();
    }

    public void checkNotFound(boolean found) {
        if (!found) {
            throw new NotFoundException();
        }
    }

    public void checkNotFoundWithId(boolean found, Long id) {
        if (!found) {
            throw new NotFoundException("not found with id: " + id);
        }
    }

    public void checkIdEquality(Long id, HasIdDto entity) {
        if (!String.valueOf(id).equals(entity.getId()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    public boolean validateEmail(String email) {
        return validateValue(emailPatterns, email);
    }

    public void validateContact(AbstractContactDto contactDto) {
        ContactType type = ContactType.getByValue(contactDto.getType());
        boolean valid;
        if (type.equals(ContactType.PHONE))
            valid = validateValue(phonePatterns, contactDto.getValue());
        else
            valid = validateValue(emailPatterns, contactDto.getValue());
        if (!valid)
            throw new ValidationException("not valid contact value: " + contactDto.getValue());
    }

    private boolean validateValue(List<String> patterns, String value) {
        return patterns.stream().anyMatch(value::matches);
    }
}
