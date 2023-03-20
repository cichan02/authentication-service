package by.piskunou.solvdlaba.service;

import by.piskunou.solvdlaba.domain.User;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;

public interface JwtService {

    Mono<String> extractUsername(String jwt);

    Mono<String> generateAccessToken(User user);

    Mono<String> generateRefreshToken(User user);

    String generateEditPasswordToken(UserDetails userDetails);

    Mono<Boolean> isValidAccessToken(String jwt);

    boolean isValidRefreshToken(String jwt);

    boolean isValidEditPasswordToken(String jwt);

}
