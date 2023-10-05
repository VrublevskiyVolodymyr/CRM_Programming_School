package ua.com.owu.crm_programming_school.services.adminService;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

import ua.com.owu.crm_programming_school.models.*;

import java.util.List;


public interface AdminService {
    public ResponseEntity<UserResponse> registerManager(UserRequest userRequest, HttpServletResponse response);

    ResponseEntity<ResponseAccess> requestToken(Integer id);

    boolean adminExists();

    void createAdminDefault();

    ResponseEntity<User> banManager(Integer id);

    ResponseEntity<User> unbanManager(Integer id);

    ResponseEntity<UserPaginated> getAll(Integer page);
}