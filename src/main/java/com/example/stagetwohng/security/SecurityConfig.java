package com.example.stagetwohng.security;

import com.example.stagetwohng.repository.UserRepository;
import com.example.stagetwohng.security.filter.HngAuthenticationFilter;
import com.example.stagetwohng.security.filter.HngAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration
public class SecurityConfig {
    private final JwtUtils jwtUtil;

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    public SecurityConfig(JwtUtils jwtUtil, UserRepository userRepository, AuthenticationManager authenticationManager) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        UsernamePasswordAuthenticationFilter authenticationFilter = new HngAuthenticationFilter(jwtUtil, userRepository, authenticationManager);
       HngAuthorizationFilter authorizationFilter = new HngAuthorizationFilter(jwtUtil);
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(authorizationFilter, HngAuthenticationFilter.class)
                .addFilterAt(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(request -> request.requestMatchers("/login", "/auth/register").permitAll()
                        .requestMatchers("/api/organisations", "/api/user/{id}", "/api/organisations/{orgId}", "/api/organisations", "/api/organisations/{orgId}/users").authenticated())
                .build();
    }
}
