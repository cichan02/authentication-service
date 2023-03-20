package by.piskunou.solvdlaba.service;

import by.piskunou.solvdlaba.domain.User;
import by.piskunou.solvdlaba.domain.event.UpdatePasswordEvent;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<User> findByUsername(String username);

    Mono<User> findByEmail(String email);

    Mono<Void> updatePasswordByUsername(UpdatePasswordEvent updatePasswordEvent);

}
