package ru.smaginv.debtmanager.util.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.smaginv.debtmanager.config.PropertiesConfig;
import ru.smaginv.debtmanager.entity.contact.ContactType;
import ru.smaginv.debtmanager.util.exception.NotFoundException;
import ru.smaginv.debtmanager.web.dto.HasIdDto;
import ru.smaginv.debtmanager.web.dto.contact.ContactDto;

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

    public void checkIsNew(HasIdDto entity) {
        if (!entity.isNew()) {
            throw new IllegalArgumentException(entity.getClass().getSimpleName() + " must be new (id = null)");
        }
    }

    public void assureIdConsistent(HasIdDto entity, String id) {
        if (entity.isNew())
            entity.setId(id);
        else if (!entity.getId().equals(id))
            throw new IllegalArgumentException(entity.getClass().getSimpleName() + " must be with id: " + id);
    }

    public boolean validateEmail(String email) {
        return validateValue(emailPatterns, email);
    }

    public void validateContact(ContactDto contactDto) {
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
