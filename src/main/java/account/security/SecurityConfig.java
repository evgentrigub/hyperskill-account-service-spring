package account.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static javax.swing.text.html.FormSubmitEvent.MethodType.POST;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    public final UserDetailsServiceImpl userDetailsService;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable) // For Postman
            .authorizeHttpRequests(auth -> {
//                 to return 400 instead of 401 for open endpoints
//                 (endpoints redirecting to the /error/** in case of error and /error/ is secured by spring security)
                auth.requestMatchers("/actuator/shutdown", "/error/**").permitAll();
                auth.requestMatchers(HttpMethod.POST, "/api/auth/signup").permitAll();
                auth.anyRequest().authenticated();
            })
            .httpBasic(Customizer.withDefaults())
            .userDetailsService(userDetailsService);

        return http.build();
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
