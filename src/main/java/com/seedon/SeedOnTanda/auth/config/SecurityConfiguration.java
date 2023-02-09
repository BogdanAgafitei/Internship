package com.seedon.SeedOnTanda.auth.config;

import com.seedon.SeedOnTanda.enums.roles.RoleValues;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .cors()
                .and().csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().exceptionHandling().authenticationEntryPoint((req, resp, e) -> {
                    System.out.println("Entry point failure: " + e.getLocalizedMessage());
                    resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                })
                .and().addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .securityMatcher("/api/v1/")
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/v1/").hasAuthority(RoleValues.ROLE_ADMIN.name())
                        .requestMatchers(HttpMethod.POST, "/api/v1/").hasAuthority(RoleValues.ROLE_ADMIN.name())
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/").hasAuthority(RoleValues.ROLE_ADMIN.name())
                        .requestMatchers(HttpMethod.GET, "/api/v1/users/").permitAll()
                        .anyRequest().hasRole("ROLE_ADMIN"))
                .authenticationProvider(authenticationProvider)
                .httpBasic(withDefaults());
        return http.build();
    }
}
