package by.piskunou.solvdlaba.service;

import by.piskunou.solvdlaba.domain.User;

public interface UserService {

    User findByUsername(String username);

    User findByEmail(String email);

    void updatePasswordByUsername(String username, String password);

}
