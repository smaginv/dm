package ru.smaginv.debtmanager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.smaginv.debtmanager.service.user.UserService;
import ru.smaginv.debtmanager.web.AuthUser;

import static ru.smaginv.debtmanager.util.AppUtil.ADMIN;
import static ru.smaginv.debtmanager.util.AppUtil.USER;

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
                .antMatchers("/user/register").anonymous()
                .antMatchers("/accounts/**").hasAnyRole(ADMIN, USER)
                .antMatchers("/contacts/**").hasAnyRole(ADMIN, USER)
                .antMatchers("/operations/**").hasAnyRole(ADMIN, USER)
                .antMatchers("/people/**").hasAnyRole(ADMIN, USER)
                .antMatchers("/user/**").hasAnyRole(ADMIN, USER)
                .antMatchers("/**").hasRole(ADMIN)
                .and().httpBasic()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().csrf().disable()
                .build();
    }
}
