package ru.smaginv.debtmanager.lm.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Configuration
public class PropertiesConfig {

    @Bean
    @ConfigurationProperties(prefix = "pattern")
    public Pattern pattern() {
        return new Pattern();
    }

    @Bean
    @ConfigurationProperties(prefix = "kafka")
    public Kafka kafka() {
        return new Kafka();
    }

    @Getter
    @Setter
    @Validated
    public static class Pattern {
        @NotBlank
        private String date;
        @NotBlank
        private String dateTime;
        @NotNull
        private List<@NotBlank String> emails;
    }

    @Getter
    @Setter
    @Validated
    public static class Kafka {
        @NotBlank
        private String bootstrapServer;
        @NotBlank
        private String topic;
        @NotBlank
        private String groupId;
    }
}
