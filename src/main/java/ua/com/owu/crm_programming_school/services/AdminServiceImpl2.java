package ua.com.owu.crm_programming_school.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ua.com.owu.crm_programming_school.models.ResponseAccess;
import ua.com.owu.crm_programming_school.models.User;
import ua.com.owu.crm_programming_school.models.UserRequest;
import ua.com.owu.crm_programming_school.models.UserResponse;


@Service
public class AdminServiceImpl2 implements AdminService{
    @Override
    public ResponseEntity<UserResponse> registerManager(UserRequest userRequest) {
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
}
