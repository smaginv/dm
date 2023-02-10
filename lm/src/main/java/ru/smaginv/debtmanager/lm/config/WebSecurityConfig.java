package ru.smaginv.debtmanager.lm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.smaginv.debtmanager.lm.service.user.UserService;
import ru.smaginv.debtmanager.lm.web.AuthUser;

import static ru.smaginv.debtmanager.lm.util.AppUtil.ADMIN;
import static ru.smaginv.debtmanager.lm.util.AppUtil.USER;

@EnableWebSecurity
public class WebSecurityConfig {

    private final UserService userService;

    @Autowired
    public WebSecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username ->
                new AuthUser(userService.getByUsername(username));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests()
                .antMatchers("/logs/today").hasAnyRole(ADMIN, USER)
                .antMatchers("/logs/on-date").hasAnyRole(ADMIN, USER)
                .antMatchers("/logs/between-dates").hasAnyRole(ADMIN, USER)
                .antMatchers("/logs/find").hasAnyRole(ADMIN, USER)
                .antMatchers("/logs/**").hasRole(ADMIN)
                .antMatchers("/admin/**").hasRole(ADMIN)
                .and().httpBasic()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().csrf().disable()
                .build();
    }
}
