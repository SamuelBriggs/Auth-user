package com.example.stagetwohng.security.provider;

import com.example.stagetwohng.security.user.HngUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@AllArgsConstructor

public class HngAuthProvider implements AuthenticationProvider {

    private final HngUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Authentication authenticationResult;
        String email = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();
        authenticationResult = authenticateUser(email, password);
        if (authenticationResult == null) {
            throw new AuthenticationException("User authentication failed") {
                @Override
                public String getMessage() {
                    return super.getMessage();
                }
            };
        }
        return authenticationResult;
    }


    private Authentication authenticateUser(String principal, String password) {
        Authentication authenticationResult;
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal);
        String memberEmail = userDetails.getUsername();
        String memberPassword = userDetails.getPassword();
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        if (passwordEncoder.matches(password, memberPassword)) {
            authenticationResult = new UsernamePasswordAuthenticationToken(memberEmail, memberPassword, authorities);
            return authenticationResult;
        }
        return null;
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }



}
