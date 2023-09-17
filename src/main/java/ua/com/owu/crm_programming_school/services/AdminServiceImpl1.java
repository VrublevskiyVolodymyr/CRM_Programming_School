package ua.com.owu.crm_programming_school.services;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

import ua.com.owu.crm_programming_school.dao.UserDAO;
import ua.com.owu.crm_programming_school.models.*;

@Service
@AllArgsConstructor
public class AdminServiceImpl1 implements AdminService {
    private UserDAO userDAO;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;
    public ResponseEntity<User> registerManager(User user) {
        try {
            if (user == null) {
                throw new NullPointerException("User cannot be null");
            }
            User manager = User
                    .builder()
                    .name(user.getName())
                    .surname(user.getSurname())
                    .email(user.getEmail())
                    .password(passwordEncoder.encode("1111"))
                    .roles(List.of(Role.MANAGER))
                    .createdAt(LocalDateTime.now().withNano(0))
                    .isActive(false)
                    .is_staff(true)
                    .is_superuser(false)
                    .build();

            User managerSaved = userDAO.save(manager);

            return new ResponseEntity<>(managerSaved , HttpStatus.CREATED);

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
                .isActive(true)
                .is_staff(true)
                .is_superuser(true)
                .build();

        userDAO.save(admin);


    }

    public boolean adminExists() {
        return userDAO.existsByRolesAndEmail(Role.ADMIN, "admin@gmail.com");
    }


}
