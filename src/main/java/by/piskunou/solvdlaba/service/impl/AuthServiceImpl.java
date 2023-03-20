package by.piskunou.solvdlaba.service.impl;

import by.piskunou.solvdlaba.domain.AuthEntity;
import by.piskunou.solvdlaba.domain.Password;
import by.piskunou.solvdlaba.domain.User;
import by.piskunou.solvdlaba.domain.UserDetailsImpl;
import by.piskunou.solvdlaba.domain.event.SendEmailEvent;
import by.piskunou.solvdlaba.domain.event.UpdatePasswordEvent;
import by.piskunou.solvdlaba.service.AuthService;
import by.piskunou.solvdlaba.service.EmailService;
import by.piskunou.solvdlaba.service.JwtService;
import by.piskunou.solvdlaba.service.UserService;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final JwtService jwtService;
    private final EmailService emailService;

    @Override
    public Mono<AuthEntity> refresh(AuthEntity authEntity) {
        if ( !jwtService.isValidRefreshToken( authEntity.getRefreshToken() )) {
            throw new AccessDeniedException("Access denied");
        }
        return jwtService.extractUsername( authEntity.getRefreshToken() )
                .flatMap(username -> {
                    User user =  userService.findByUsername(username);
                    return jwtService.generateAccessToken( user );
                }).flatMap(accessToken -> {
                    authEntity.setAccessToken( accessToken );
                    return Mono.just(authEntity);
                });

    }

    @Override
    public Mono<Void> createPassword(String email) {
        User user = userService.findByEmail(email);
        UserDetailsImpl userDetails = new UserDetailsImpl(user);
        String editPasswordToken = jwtService.generateEditPasswordToken( userDetails );
        SendEmailEvent sendEmailEvent = SendEmailEvent.builder()
                .uuid( UUID.randomUUID() )
                .email(email)
                .username(user.getUsername())
                .token( editPasswordToken )
                .build();
        return emailService.sendMessage( sendEmailEvent );
    }

    @Override
    public Mono<Void> editPassword(String token, Password password) {
        if ( !jwtService.isValidEditPasswordToken(token) ) {
            throw new JWTVerificationException("Invalid token");
        }
        return jwtService.extractUsername(token)
                .flatMap(username -> {
                    UpdatePasswordEvent updatePasswordEvent = UpdatePasswordEvent.builder()
                            .uuid( UUID.randomUUID() )
                            .username(username)
                            .password( password.getNewPassword() )
                            .build();
                    return userService.updatePasswordByUsername(updatePasswordEvent) ;
                });
    }

}
