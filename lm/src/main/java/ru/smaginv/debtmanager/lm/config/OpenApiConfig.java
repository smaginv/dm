package ru.smaginv.debtmanager.lm.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.TreeMap;

@Configuration
@SecurityScheme(
        name = "basicAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "basic"
)
@OpenAPIDefinition(
        info = @Info(
                title = "REST API documentation",
                version = "1.0",
                description = "Log manager"
        ),
        security = @SecurityRequirement(name = "basicAuth")
)
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi
                .builder()
                .addOpenApiCustomiser(sortSchemas())
                .displayName("Administrator")
                .group("admin")
                .pathsToMatch("/**")
                .build();
    }

    @Bean
    public GroupedOpenApi userApi() {
        String[] includedPaths = {"/logs/today", "/logs/on-date", "/logs/between-dates", "/logs/find"};
        return GroupedOpenApi
                .builder()
                .addOpenApiCustomiser(sortSchemas())
                .displayName("User")
                .group("user")
                .pathsToMatch(includedPaths)
                .build();
    }

    @Bean
    public OpenApiCustomiser sortSchemas() {
        return openApi ->
                openApi.getComponents().setSchemas(new TreeMap<>(openApi.getComponents().getSchemas()));
    }
}
