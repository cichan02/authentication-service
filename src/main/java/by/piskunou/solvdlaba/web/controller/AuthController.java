package by.piskunou.solvdlaba.web.controller;

import by.piskunou.solvdlaba.domain.AuthEntity;
import by.piskunou.solvdlaba.service.AuthService;
import by.piskunou.solvdlaba.web.dto.AuthEntityDTO;
import by.piskunou.solvdlaba.web.dto.PasswordDTO;
import by.piskunou.solvdlaba.web.mapper.AuthMapper;
import by.piskunou.solvdlaba.web.mapper.PasswordMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final AuthService authService;
    private final AuthMapper authMapper;
    private final PasswordMapper passwordMapper;

    @PostMapping("/refresh")
    @Operation(summary = "Refresh jwt access token by valid refresh jwt token")
    @Parameter(name = "authEntityDTO", description = "Entity with access and refresh jwt token")
    public Mono<AuthEntityDTO> refresh(@RequestBody @Validated AuthEntityDTO authEntityDTO) {
        AuthEntity authEntity = authMapper.toEntity(authEntityDTO);
        return authService.refresh(authEntity).map(authMapper::toDTO);
    }

    @GetMapping("/password/new")
    @Operation(summary = "Forgot password: put in an email and get link to create new password")
    @Parameter(name = "email", description = "Email where to send a change link")
    public Mono<Void> newPassword(@RequestParam("user_email") @Email(message = "Must be a well-formed email address") String email) {
        return authService.createPassword(email);
    }

    @PostMapping("/password/edit")
    @Operation(summary = "Set up new password")
    @Parameter(name = "token", description = "one-time token to set new password")
    public Mono<Void> editPassword(@RequestParam("reset_password_token") String token, @RequestBody @Validated(PasswordDTO.onEdit.class) PasswordDTO dto) {
        return authService.editPassword(token, passwordMapper.toEntity(dto));
    }

}
