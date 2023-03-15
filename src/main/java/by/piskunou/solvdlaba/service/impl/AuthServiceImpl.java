package by.piskunou.solvdlaba.service.impl;

import by.piskunou.solvdlaba.domain.AuthEntity;
import by.piskunou.solvdlaba.domain.Password;
import by.piskunou.solvdlaba.domain.User;
import by.piskunou.solvdlaba.domain.UserDetailsImpl;
import by.piskunou.solvdlaba.service.AuthService;
import by.piskunou.solvdlaba.service.EmailService;
import by.piskunou.solvdlaba.service.JwtService;
import by.piskunou.solvdlaba.service.UserService;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final JwtService jwtService;
    private final EmailService emailService;

    @Override
    public AuthEntity refresh(AuthEntity authEntity) {
        if(!jwtService.isValidRefreshToken(authEntity.getRefreshToken())) {
            throw new AccessDeniedException("Access denied");
        }
        String username = jwtService.extractUsername( authEntity.getRefreshToken() );
        UserDetailsImpl userDetails = new UserDetailsImpl( userService.findByUsername(username) );
        authEntity.setAccessToken( jwtService.generateAccessToken(userDetails)  );
        return authEntity;
    }

    @Override
    public void createPassword(String email) {
        User user = userService.findByEmail(email);
        UserDetailsImpl userDetails = new UserDetailsImpl(user);
        String editPasswordToken = jwtService.generateEditPasswordToken(userDetails);

        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("user", user);
        templateModel.put("token", editPasswordToken);

        emailService.sendMessage(email, templateModel);
    }

    @Override
    public void editPassword(String token, Password password) {
        if(!jwtService.isValidEditPasswordToken(token)) {
            throw new JWTVerificationException("Invalid token");
        }
        String username = jwtService.extractUsername(token);
        userService.updatePasswordByUsername(username, password.getNewPassword());
    }

}
