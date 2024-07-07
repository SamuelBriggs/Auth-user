package com.example.stagetwohng.security.filter;

import com.auth0.jwt.interfaces.Claim;
import com.example.stagetwohng.security.EndPointConstant;
import com.example.stagetwohng.security.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
@Component
public class HngAuthorizationFilter extends OncePerRequestFilter {

        private final JwtUtils jwtUtil;

    public HngAuthorizationFilter(JwtUtils jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, RuntimeException {
            boolean isUnAuthorizedPath = EndPointConstant.UNAUTHORIZEDENDPOINTS.contains(request.getServletPath()) &&
                    request.getMethod().equals(HttpMethod.POST.name());
            if (isUnAuthorizedPath) filterChain.doFilter(request, response);
            else {
                authorizeRequest(request, response, filterChain);
            }
        }

        @SneakyThrows
        private void authorizeRequest(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            authorize(request);
            filterChain.doFilter(request, response);
        }

        private void authorize(HttpServletRequest request)  {
            String authorization = request.getHeader(AUTHORIZATION);
            String tokenPrefix = "Bearer ";
            boolean isValidAuthorizationHeader = false;
            if (authorization != null && authorization.startsWith(tokenPrefix))
                isValidAuthorizationHeader = true;

            if (isValidAuthorizationHeader) {
                String token = authorization.substring(tokenPrefix.length());
                authorizeToken(token);
            }
        }

        private void authorizeToken(String token) {
            Map<String, Claim> map = jwtUtil.extractClaimsFromToken(token);
            Claim email = map.get("email");
            Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, null);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }


}
