package by.piskunou.solvdlaba.service;

import by.piskunou.solvdlaba.domain.AuthEntity;
import by.piskunou.solvdlaba.domain.Password;

public interface AuthService {

    AuthEntity refresh(AuthEntity authEntity);

    void createPassword(String email);

    void editPassword(String token, Password password);

}
