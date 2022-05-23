package ru.smaginv.debtmanager.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Configuration
public class PropertiesConfig {

    @Bean
    @ConfigurationProperties(prefix = "pattern")
    public Pattern pattern() {
        return new Pattern();
    }

    @Getter
    @Setter
    @Validated
    public static class Pattern {
        @NotEmpty
        private List<@NotBlank String> phones;
        @NotEmpty
        private List<@NotBlank String> emails;
        @NotBlank
        private String date;
        @NotBlank
        private String dateTime;
    }
}
