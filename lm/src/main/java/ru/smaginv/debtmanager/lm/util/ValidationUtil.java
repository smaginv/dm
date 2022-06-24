package ru.smaginv.debtmanager.lm.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.smaginv.debtmanager.lm.config.PropertiesConfig;
import ru.smaginv.debtmanager.lm.util.exception.NotFoundException;

import java.util.List;

@Component
public class ValidationUtil {

    private final List<String> emailPatterns;

    @Autowired
    public ValidationUtil(PropertiesConfig propertiesConfig) {
        this.emailPatterns = propertiesConfig.pattern().getEmails();
    }

    public void checkNotFound(boolean found) {
        if (!found)
            throw new NotFoundException();
    }

    public boolean validateEmail(String email) {
        return emailPatterns.stream().anyMatch(email::matches);
    }
}
