package ua.com.owu.crm_programming_school.services;

import org.springframework.http.ResponseEntity;

import ua.com.owu.crm_programming_school.models.ResponseAccess;
import ua.com.owu.crm_programming_school.models.User;



public interface AdminService {
    public ResponseEntity<User> registerManager(User user);

    ResponseEntity<ResponseAccess> requestToken(Integer id);

    boolean adminExists();

    void createAdminDefault();
}