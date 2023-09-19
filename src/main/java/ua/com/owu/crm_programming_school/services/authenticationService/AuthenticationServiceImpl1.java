package ua.com.owu.crm_programming_school.services.authenticationService;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ua.com.owu.crm_programming_school.dao.UserDAO;
import ua.com.owu.crm_programming_school.models.*;
import ua.com.owu.crm_programming_school.services.jwtService.JwtService;

import java.io.IOException;
import java.time.LocalDateTime;


@Service
@AllArgsConstructor
public class AuthenticationServiceImpl1 implements AuthenticationService {
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

            user.set_active(true);
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

    public ResponseEntity<AuthenticationResponse> refresh(RequestRefresh requestRefresh, HttpServletResponse response) {
        try {
            String accessToken = requestRefresh.getRefresh();
            String username = jwtService.extractUsername(accessToken);
            User user = userDAO.findByEmail(username);
            String newAccessToken = null;
            String newRefreshToken = null;

            if (user.getRefreshToken().equals(accessToken)) {
                newAccessToken = jwtService.generateToken(user);
                newRefreshToken = jwtService.generateRefreshToken(user);
                user.setRefreshToken(newRefreshToken);
                userDAO.save(user);
            }

            AuthenticationResponse responseAuth = AuthenticationResponse
                    .builder()
                    .access(newAccessToken)
                    .refresh(newRefreshToken)
                    .build();

            return new ResponseEntity<>(responseAuth, HttpStatus.OK);

        } catch (SignatureException e) {
            response.setHeader("TokenError", "Token is not valid");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            ResponseError responseError = ResponseError
                    .builder()
                    .error("Token is not valid")
                    .code(403)
                    .build();

            try {
                response.getOutputStream().write(new ObjectMapper().writeValueAsBytes(responseError));
                response.getOutputStream().flush();
                response.getOutputStream().close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

}
