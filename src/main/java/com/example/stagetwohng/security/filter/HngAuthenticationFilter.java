package com.example.stagetwohng.security.filter;

import com.example.stagetwohng.dtos.requests.LoginRequest;
import com.example.stagetwohng.dtos.responses.ApiResponse;
import com.example.stagetwohng.model.User;
import com.example.stagetwohng.repository.UserRepository;
import com.example.stagetwohng.security.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


public class HngAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private String email;
    private String password;

    private final JwtUtils jwtUtil;

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    public HngAuthenticationFilter(JwtUtils jwtUtils, UserRepository userRepository, AuthenticationManager authenticationManager) {
        this.jwtUtil = jwtUtils;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
    }


    @Override
        public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
            try {
                LoginRequest loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);
                email = loginRequest.getEmail();
                password = loginRequest.getPassword();
                Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
                Authentication authenticationResult = authenticationManager.authenticate(authentication);
                SecurityContextHolder.getContext().setAuthentication(authenticationResult);
                return authenticationResult;
            } catch (IOException exception) {
                throw new BadCredentialsException("Failed to authenticate");
            }
        }

        @SneakyThrows
        @Override
        protected void successfulAuthentication(HttpServletRequest request,
                                                HttpServletResponse response,
                                                FilterChain chain,
                                                Authentication authResult) throws IOException {
            Optional<User> foundUser = userRepository.findByEmail(email);
            String accessToken = jwtUtil.generateAccessToken(email);
            Map<String, Object> responseData = new HashMap<>();
//        ApiResponse<?> successResponse = ApiResponse.builder().success(true).message("Login successful").data(accessToken).build();
            responseData.put("access_token", accessToken);
//        responseData.put("", successResponse);
            response.setContentType(APPLICATION_JSON_VALUE);
            response.getOutputStream().write(objectMapper.writeValueAsBytes(
                    responseData));
        }


//        @Override
//        protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
//            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            ApiResponse<?> errorResponse = ApiResponse.builder().success(false).message("Login Error, please try again").build();
//            ObjectMapper mapper = new ObjectMapper();
//            String jsonResponse = mapper.writeValueAsString(errorResponse);
//            response.setContentType(APPLICATION_JSON_VALUE);
//            response.getOutputStream().write(jsonResponse.getBytes());
//        }


    }


