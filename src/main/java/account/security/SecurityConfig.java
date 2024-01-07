package account.security;

import account.exceptions.AccessDeniedHandlerImpl;
import account.models.role.RoleType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // For Postman
                .authorizeHttpRequests(auth -> {
                    // to return 400 instead of 401 for open endpoints
                    // (endpoints redirecting to the /error/** in case of error and /error/ is secured by spring security)
                    auth.requestMatchers("/actuator/shutdown", "/error/**").permitAll();
                    auth.requestMatchers(toH2Console()).permitAll();

                    auth.requestMatchers("/api/auth/signup/**")
                            .permitAll();
                    auth.requestMatchers("/api/auth/changepass/**")
                            .authenticated();
                    auth.requestMatchers("/api/acct/**")
                            .hasRole(RoleType.ACCOUNTANT.toString());
                    auth.requestMatchers("/api/empl/**")
                            .hasAnyRole(RoleType.ACCOUNTANT.toString(), RoleType.USER.toString());
                    auth.requestMatchers("/api/admin/**")
                            .hasRole(RoleType.ADMINISTRATOR.toString());
                })
                .userDetailsService(userDetailsService)
                .httpBasic(Customizer.withDefaults())
                .exceptionHandling() // TODO deprecated
                .accessDeniedHandler(new AccessDeniedHandlerImpl())
                .and()
                .sessionManagement(sessions -> sessions.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(13);
    }
}
