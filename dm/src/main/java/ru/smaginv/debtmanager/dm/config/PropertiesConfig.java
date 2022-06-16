package ru.smaginv.debtmanager.dm.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Configuration
public class PropertiesConfig {

    @Bean
    @ConfigurationProperties(prefix = "pattern")
    public Pattern pattern() {
        return new Pattern();
    }

    @Bean
    @ConfigurationProperties(prefix = "cache")
    public Cache cache() {
        return new Cache();
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
        @NotEmpty
        private List<@NotBlank String> phones;
        @NotEmpty
        private List<@NotBlank String> emails;
        @NotBlank
        private String date;
        @NotBlank
        private String dateTime;
    }

    @Getter
    @Setter
    @Validated
    public static class Cache {
        @NotNull
        private Long entries;
        @NotNull
        private Long heapSize;
        @NotNull
        private Long duration;
        @NotEmpty
        private List<@NotBlank String> values;
    }

    @Getter
    @Setter
    @Validated
    public static class Kafka {
        @NotEmpty
        private List<@NotBlank String> bootstrapServers;
        @NotEmpty
        private Map<@NotBlank String, @NotEmpty Map<@NotBlank String, @NotNull Integer>> topics;
    }
}
