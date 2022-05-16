package ru.smaginv.debtmanager.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.smaginv.debtmanager.config.AppProperties;
import ru.smaginv.debtmanager.entity.contact.ContactType;
import ru.smaginv.debtmanager.web.dto.HasIdDto;
import ru.smaginv.debtmanager.web.dto.contact.ContactDto;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Component
public class ValidationUtil {

    private final List<String> phonePatterns;
    private final List<String> emailPatterns;

    @Autowired
    public ValidationUtil(AppProperties appProperties) {
        this.phonePatterns = appProperties.pattern().getPhones();
        this.emailPatterns = appProperties.pattern().getEmails();
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
            throw new IllegalArgumentException(entity + " must be new (id=null)");
        }
    }

    public void checkContact(ContactDto contactDto) {
        ContactType type = ContactType.getByValue(contactDto.getType());
        if (type.equals(ContactType.PHONE))
            checkContactValue(phonePatterns, contactDto.getValue());
        else
            checkContactValue(emailPatterns, contactDto.getValue());
    }

    public void checkContactValue(List<String> patterns, String value) {
        if (patterns.stream().noneMatch(value::matches))
            throw new IllegalArgumentException("not valid contact value");
    }
}
