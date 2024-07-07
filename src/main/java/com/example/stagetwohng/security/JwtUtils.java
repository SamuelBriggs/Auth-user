package com.example.stagetwohng.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
@AllArgsConstructor
public class JwtUtils {
    @Value("JWT_SECRET")
    private final String secret;


    public Map<String, Claim> extractClaimsFromToken(String token)  {
        DecodedJWT decodedJwt = verifyToken(token);
        return decodedJwt.getClaims();
    }

    public DecodedJWT verifyToken(String token) {
        Algorithm algorithm = Algorithm.HMAC512(secret.getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }

    public String generateAccessToken(String email) {


        return JWT.create()
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plusSeconds(86000L))
                .withClaim("email", email)
                .sign(Algorithm.HMAC512(secret.getBytes()));
    }

    public String generateAccessTokenForOAuth(String email) {
        return JWT.create().withIssuedAt(Instant.now()).withExpiresAt(Instant.now().plusSeconds(86000L))
                .withClaim("email", email)
                .sign(Algorithm.HMAC512(secret.getBytes()));
    }

    public String fetchToken(String email) {
        return JWT.create().withIssuedAt(Instant.now()).withExpiresAt(Instant.now().plusSeconds(86000L))
                .withClaim("email", email)
                .sign(Algorithm.HMAC512(secret.getBytes()));
    }


}
