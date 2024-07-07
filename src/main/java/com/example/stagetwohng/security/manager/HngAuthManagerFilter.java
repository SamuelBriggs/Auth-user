package com.example.stagetwohng.security.manager;

import com.example.stagetwohng.exceptions.HngException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class HngAuthManagerFilter implements AuthenticationManager {

    private final AuthenticationProvider authenticationProvider;

    public HngAuthManagerFilter(AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Authentication authenticationResult;
        if (authenticationProvider.supports(authentication.getClass())) {
            authenticationResult = authenticationProvider.authenticate(authentication);
            return authenticationResult;
        } else throw new HngException("Failed to Authenticate");
    }


}
