package ua.com.owu.crm_programming_school.services.adminService;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ua.com.owu.crm_programming_school.models.*;
import ua.com.owu.crm_programming_school.services.adminService.AdminService;

import java.util.List;


@Service
public class AdminServiceImpl2 implements AdminService {
    @Override
    public ResponseEntity<UserResponse> registerManager(UserRequest userRequest, HttpServletResponse response) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseAccess> requestToken(Integer id) {
        return null;
    }

    @Override
    public boolean adminExists() {
        return false;
    }

    @Override
    public void createAdminDefault() {

    }

    @Override
    public ResponseEntity<User> banManager(Integer id) {
        return null;
    }

    @Override
    public ResponseEntity<User> unbanManager(Integer id) {
        return null;
    }

    @Override
    public ResponseEntity<UserPaginated> getAll(Integer page) {
        return null;
    }

}
