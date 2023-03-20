package by.piskunou.solvdlaba.service;

import by.piskunou.solvdlaba.domain.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    String extractUsername(String jwt);

    String generateAccessToken(User user);

    String generateRefreshToken(User user);

    String generateEditPasswordToken(UserDetails userDetails);

    boolean isValidAccessToken(String jwt);

    boolean isValidRefreshToken(String jwt);

    boolean isValidEditPasswordToken(String jwt);

}
