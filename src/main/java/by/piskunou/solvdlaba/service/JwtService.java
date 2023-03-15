package by.piskunou.solvdlaba.service;

import by.piskunou.solvdlaba.domain.UserDetailsImpl;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;

public interface JwtService {

    Mono<String> extractUsername(String jwt);

    Mono<String> generateAccessToken(UserDetailsImpl userDetails);

    Mono<String> generateRefreshToken(UserDetails userDetails);

    Mono<String> generateEditPasswordToken(UserDetails userDetails);

    Mono<Boolean> isValidAccessToken(String jwt);

    Mono<Boolean> isValidRefreshToken(String jwt);

    Mono<Boolean> isValidEditPasswordToken(String jwt);

}
