package ua.com.owu.crm_programming_school.services.adminService;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

import ua.com.owu.crm_programming_school.models.ResponseAccess;
import ua.com.owu.crm_programming_school.models.User;
import ua.com.owu.crm_programming_school.models.UserRequest;
import ua.com.owu.crm_programming_school.models.UserResponse;


public interface AdminService {
    public ResponseEntity<UserResponse> registerManager(UserRequest userRequest, HttpServletResponse response);

    ResponseEntity<ResponseAccess> requestToken(Integer id);

    boolean adminExists();

    void createAdminDefault();
}