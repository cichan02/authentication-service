package by.piskunou.solvdlaba.service.impl;

import by.piskunou.solvdlaba.domain.UserDetailsImpl;
import by.piskunou.solvdlaba.domain.exception.NoSuchClaimException;
import by.piskunou.solvdlaba.service.JwtService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.ZonedDateTime;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${jwt-secret}")
    private String secretKey;

    @Override
    public Mono<String> extractUsername(String jwt) {
        try {
            DecodedJWT decodedJwt = JWT.decode(jwt);
            return Mono.just( decodedJwt.getClaim("username").asString() );
        } catch (JWTDecodeException e) {
            return Mono.error(new NoSuchClaimException("There's no username claim in jwt-token"));
        }
    }

    @Override
    public Mono<String> generateAccessToken(UserDetailsImpl userDetails) {
        String jwt = JWT.create()
                .withSubject("Access token")
                .withClaim("username", userDetails.getUsername())
                .withClaim("email", userDetails.getUser().getEmail())
                .withIssuer("Airport")
                .withIssuedAt(Instant.now())
                .withExpiresAt( ZonedDateTime.now().plusHours(1).toInstant() )
                .sign(Algorithm.HMAC256(secretKey));
        return Mono.just(jwt);
    }

    @Override
    public Mono<String> generateRefreshToken(UserDetails userDetails) {
        String jwt = JWT.create()
                  .withSubject("Refresh token")
                  .withClaim("username", userDetails.getUsername())
                  .withIssuer("Airport")
                  .withIssuedAt(Instant.now())
                  .withExpiresAt( ZonedDateTime.now().plusWeeks(1).toInstant() )
                  .sign(Algorithm.HMAC256(secretKey));
        return Mono.just(jwt);
    }

    @Override
    public Mono<String> generateEditPasswordToken(UserDetails userDetails) {
        String jwt = JWT.create()
                .withSubject("Edit password token")
                .withClaim("username", userDetails.getUsername())
                .withIssuer("Airport")
                .withIssuedAt(Instant.now())
                .withExpiresAt( ZonedDateTime.now().plusMinutes(5).toInstant() )
                .sign(Algorithm.HMAC256(secretKey));
        return Mono.just(jwt);
    }

    @Override
    public Mono<Boolean> isValidAccessToken(String jwt) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secretKey))
                                      .withSubject("Access token")
                                      .withIssuer("Airport")
                                      .build();
            verifier.verify(jwt);
            return Mono.just(true);
        } catch (JWTVerificationException e) {
            return Mono.just(false);
        }
    }

    @Override
    public Mono<Boolean> isValidRefreshToken(String jwt) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secretKey))
                                      .withSubject("Refresh token")
                                      .withIssuer("Airport")
                                      .build();
            verifier.verify(jwt);
            return Mono.just(true);
        } catch (JWTVerificationException e) {
            return Mono.just(false);
        }
    }

    @Override
    public Mono<Boolean> isValidEditPasswordToken(String jwt) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secretKey))
                                      .withSubject("Edit password token")
                                      .withIssuer("Airport")
                                      .build();
            verifier.verify(jwt);
            return Mono.just(true);
        } catch (JWTVerificationException e) {
            return Mono.just(false);
        }
    }

}
