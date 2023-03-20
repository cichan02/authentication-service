package by.piskunou.solvdlaba.service;

import by.piskunou.solvdlaba.domain.AuthEntity;
import by.piskunou.solvdlaba.domain.Password;
import reactor.core.publisher.Mono;

public interface AuthService {

    Mono<AuthEntity> refresh(AuthEntity authEntity);

    Mono<Void> createPassword(String email);

    Mono<Void> editPassword(String token, Password password);

}
