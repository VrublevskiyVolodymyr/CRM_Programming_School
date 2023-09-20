package ua.com.owu.crm_programming_school.services.adminService;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.ErrorResponse;
import ua.com.owu.crm_programming_school.dao.UserDAO;
import ua.com.owu.crm_programming_school.models.*;
import ua.com.owu.crm_programming_school.services.jwtService.JwtService;

@Service
@AllArgsConstructor
public class AdminServiceImpl1 implements AdminService {
    private UserDAO userDAO;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;
    public ResponseEntity<Object> registerManager(UserRequest userRequest) {
        try {
            if (userDAO.existsByEmail(userRequest.getEmail())) {
                ResponseError responseError = new ResponseError("Email already exists", 400);
                return new ResponseEntity<>(responseError, HttpStatus.BAD_REQUEST);
            }


                User manager = User
                    .builder()
                    .name(userRequest.getName())
                    .surname(userRequest.getSurname())
                    .email(userRequest.getEmail())
                    .password(passwordEncoder.encode("1111"))
                    .roles(List.of(Role.MANAGER))
                    .createdAt(LocalDateTime.now().withNano(0))
                    .is_active(false)
                    .is_staff(true)
                    .is_superuser(false)
                    .build();

            User managerSaved = userDAO.save(manager);

            UserResponse userResponse =UserResponse
                    .builder()
                    .id(managerSaved.getId())
                    .name(managerSaved.getName())
                    .surname(managerSaved.getSurname())
                    .email(managerSaved.getEmail())
                    .build();

            return new ResponseEntity<>(userResponse , HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ResponseAccess> requestToken(Integer id) {
        User user = userDAO.findById(id).get();
        String accessToken = jwtService.generateToken(user);

        ResponseAccess response = ResponseAccess
                .builder()
                .access(accessToken)
                .build();

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    public void createAdminDefault() {
        User admin = User
                .builder()
                .name("admin")
                .surname("adminovych")
                .email("admin@gmail.com")
                .password(passwordEncoder.encode("admin"))
                .roles(List.of(Role.ADMIN))
                .createdAt(LocalDateTime.now().withNano(0))
                .is_active(true)
                .is_staff(true)
                .is_superuser(true)
                .build();

        userDAO.save(admin);

    }

    public boolean adminExists() {
        return userDAO.existsByRolesAndEmail(Role.ADMIN, "admin@gmail.com");
    }


}
