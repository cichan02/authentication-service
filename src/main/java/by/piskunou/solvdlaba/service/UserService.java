package by.piskunou.solvdlaba.service;

import by.piskunou.solvdlaba.domain.User;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<User> findByUsername(String username);

    Mono<User> findByEmail(String email);

    Mono<Void> updatePasswordByUsername(String username, String password);

}
