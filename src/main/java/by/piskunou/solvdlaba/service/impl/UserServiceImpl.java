package by.piskunou.solvdlaba.service.impl;

import by.piskunou.solvdlaba.domain.User;
import by.piskunou.solvdlaba.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    //todo: rewrite
    @Override
    public User findByUsername(String username) {
        return null;
    }

    //todo: rewrite
    @Override
    public User findByEmail(String email) {
        return null;
    }

    //todo: rewrite
    @Override
    public void updatePasswordByUsername(String username, String password) {

    }

}
