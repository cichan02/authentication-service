package by.piskunou.solvdlaba.service.impl;

import by.piskunou.solvdlaba.domain.AuthEntity;
import by.piskunou.solvdlaba.domain.Password;
import by.piskunou.solvdlaba.domain.SendEmailEvent;
import by.piskunou.solvdlaba.domain.UpdatePasswordEvent;
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
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final JwtService jwtService;
    private final EmailService emailService;

    @Override
    public Mono<AuthEntity> refresh(AuthEntity authEntity) {
        return jwtService.isValidRefreshToken( authEntity.getRefreshToken() )
                .flatMap(validRefreshToken -> {
                    if(!validRefreshToken) {
                        return Mono.error(new AccessDeniedException("Access denied"));
                    }
                    return jwtService.extractUsername( authEntity.getRefreshToken() );
                }).flatMap(username -> {
                    UserDetailsImpl userDetails = new UserDetailsImpl( userService.findByUsername(username) );
                    return jwtService.generateAccessToken(userDetails);
                }).flatMap(refreshToken -> {
                    authEntity.setAccessToken( refreshToken );
                    return Mono.just(authEntity);
                });
    }

    @Override
    public Mono<Void> createPassword(String email) {
        User user = userService.findByEmail(email);
        UserDetailsImpl userDetails = new UserDetailsImpl(user);
        return jwtService.generateEditPasswordToken(userDetails)
                .flatMap(editPasswordToken -> {
                    SendEmailEvent sendEmailEvent = SendEmailEvent.builder()
                                    .email(email)
                                    .username(user.getUsername())
                                    .token(editPasswordToken)
                                    .build();
                    return emailService.sendMessage(sendEmailEvent);
                });
    }

    @Override
    public Mono<Void> editPassword(String token, Password password) {
        return jwtService.isValidEditPasswordToken(token)
                .flatMap(validEditPasswordToken -> {
                    if(!validEditPasswordToken) {
                        return Mono.error(new JWTVerificationException("Invalid token"));
                    }
                    return jwtService.extractUsername(token);
                }).flatMap(username -> {
                    UpdatePasswordEvent updatePasswordEvent = UpdatePasswordEvent.builder()
                            .username(username)
                            .password( password.getNewPassword() )
                            .build();
                    return userService.updatePasswordByUsername(updatePasswordEvent) ;
                });
    }

}
