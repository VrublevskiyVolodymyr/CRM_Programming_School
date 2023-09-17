package ua.com.owu.crm_programming_school.services;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ua.com.owu.crm_programming_school.dao.UserDAO;
import ua.com.owu.crm_programming_school.models.*;

import java.time.LocalDateTime;


@Service
@AllArgsConstructor
public class AuthenticationService {
    private UserDAO userDAO;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;


    public ResponseEntity activate(String token, Password password) {

        String username = jwtService.extractUsername(token);
        User user = userDAO.findByEmail(username);
        try {
            if (user == null) {
                throw new NullPointerException("User cannot be null");
            }

            user.setActive(true);
            user.setPassword(passwordEncoder.encode(password.getPassword()));
            userDAO.save(user);

            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<AuthenticationResponse> authenticate(TokenObtainPair tokenObtainPair) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        tokenObtainPair.getUsername(),
                        tokenObtainPair.getPassword()
                )
        );
        User user = userDAO.findByEmail(tokenObtainPair.getUsername());
        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        user.setRefreshToken(refreshToken);
        user.setLastLogin(LocalDateTime.now().withNano(0));
        userDAO.save(user);

        AuthenticationResponse response = AuthenticationResponse
                .builder()
                .access(accessToken)
                .refresh(refreshToken)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<AuthenticationResponse> refresh(RequestRefresh requestRefresh) {
        String accessToken = requestRefresh.getRefresh();
        String username = jwtService.extractUsername(accessToken);
        User user = userDAO.findByEmail(username);
        String newAccessToken = null;
        String newRefreshToken = null;

        if (user
                .getRefreshToken()
                .equals(accessToken)) {

            newAccessToken = jwtService.generateToken(user);
            newRefreshToken = jwtService.generateRefreshToken(user);
            user.setRefreshToken(newRefreshToken);
            userDAO.save(user);
        }

        AuthenticationResponse response = AuthenticationResponse
                .builder()
                .access(newAccessToken)
                .refresh(newRefreshToken)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
