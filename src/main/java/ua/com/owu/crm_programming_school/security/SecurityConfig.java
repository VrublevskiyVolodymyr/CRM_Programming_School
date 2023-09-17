package ua.com.owu.crm_programming_school.security;


import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import static org.springframework.security.config.Customizer.withDefaults;

import ua.com.owu.crm_programming_school.security.filter.JWTFilter;

@AllArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private JWTFilter jwtFilter;
    private AuthenticationProvider authenticationProvider;

    @Bean
    @SneakyThrows
    public SecurityFilterChain filterChain(HttpSecurity http) {
        http
                .csrf(AbstractHttpConfigurer::disable).cors(withDefaults())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((authorizeHttpRequests) ->
                        authorizeHttpRequests
                                .requestMatchers(HttpMethod.POST,"/api/v1/auth","/api/v1/auth/refresh","/api/v1/auth/activate/{token}").permitAll()
                                .requestMatchers(HttpMethod.GET, "/v2/api-docs", "/v3/api-docs", "/v3/api-docs/**", "/swagger-resources", "/swagger-resources/**", "/configuration/ui", "/configuration/security", "/swagger-ui.html", "/api/v1/doc/**", "/swagger-ui/**", "/webjars/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/admin/users").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/v1/admin/users/{id}/re_token").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/v1/orders/**").hasAnyAuthority("MANAGER", "ADMIN"))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}