package ru.smaginv.debtmanager.util.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.smaginv.debtmanager.config.PropertiesConfig;
import ru.smaginv.debtmanager.entity.contact.ContactType;
import ru.smaginv.debtmanager.web.dto.HasIdDto;
import ru.smaginv.debtmanager.web.dto.contact.AbstractContactDto;

import javax.persistence.EntityNotFoundException;
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
            throw new EntityNotFoundException();
        }
    }

    public void checkNotFoundWithId(boolean found, Long id) {
        if (!found) {
            throw new EntityNotFoundException("not found with id: " + id);
        }
    }

    public void checkIsNew(HasIdDto entity) {
        if (!entity.isNew()) {
            throw new IllegalArgumentException(entity + " must be new (id = null)");
        }
    }

    public void assureIdConsistent(HasIdDto entity, String id) {
        if (entity.isNew())
            entity.setId(id);
        else if (!entity.getId().equals(id))
            throw new IllegalArgumentException(entity + " must be with id: " + id);
    }

    public void validateContact(AbstractContactDto contactDto) {
        ContactType type = ContactType.getByValue(contactDto.getType());
        if (type.equals(ContactType.PHONE))
            validateValue(phonePatterns, contactDto.getValue());
        else
            validateValue(emailPatterns, contactDto.getValue());
    }

    private void validateValue(List<String> patterns, String value) {
        if (patterns.stream().noneMatch(value::matches))
            throw new ValidationException("not valid contact value: " + value);
    }
}
