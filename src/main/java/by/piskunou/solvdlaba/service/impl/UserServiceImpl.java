package by.piskunou.solvdlaba.service.impl;

import by.piskunou.solvdlaba.domain.User;
import by.piskunou.solvdlaba.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    //todo: rewrite
    @Override
    public Mono<User> findByUsername(String username) {
        return Mono.empty();
    }

    //todo: rewrite
    @Override
    public Mono<User> findByEmail(String email) {
        return Mono.empty();
    }

    //todo: rewrite
    @Override
    public Mono<Void> updatePasswordByUsername(String username, String password) {
        return Mono.empty();
    }

}
