package com.ibm.mateusmelo.configuration;

import com.ibm.mateusmelo.security.jwt.JwtService;
import com.ibm.mateusmelo.security.jwt.filter.JwtAuthFilter;
import com.ibm.mateusmelo.service.impl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    private final JwtService jwtService;
    private final UserServiceImpl userService;

    public SecurityConfig(JwtService jwtService, UserServiceImpl userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Bean
    public OncePerRequestFilter jwtFilter() {
        return new JwtAuthFilter(jwtService, userService);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        clientRules -> clientRules.requestMatchers("/api/clients/**")
                                .hasAnyRole("ADMIN", "USER")
                ).authorizeHttpRequests(
                        productRules -> productRules.requestMatchers("/api/products/**")
                                .hasRole("ADMIN")
                ).authorizeHttpRequests(
                        orderRules -> orderRules.requestMatchers("/api/orders/**")
                                .hasAnyRole("ADMIN", "USER")
                ).authorizeHttpRequests(
                        userRules -> userRules.requestMatchers(HttpMethod.POST, "/api/users/**")
                                .permitAll()
                ).authorizeHttpRequests(
                        any -> any.requestMatchers("/h2-console/**")
                                .hasRole("ADMIN")
                )
                .authorizeHttpRequests(
                        any -> any.anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-resources/**"
                );
    }
}
