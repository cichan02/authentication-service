package by.piskunou.solvdlaba.service;

import by.piskunou.solvdlaba.domain.UpdatePasswordEvent;
import by.piskunou.solvdlaba.domain.User;
import reactor.core.publisher.Mono;

public interface UserService {

    User findByUsername(String username);

    User findByEmail(String email);

    Mono<Void> updatePasswordByUsername(UpdatePasswordEvent updatePasswordEvent);

}
