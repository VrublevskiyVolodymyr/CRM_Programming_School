package ua.com.owu.crm_programming_school.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ua.com.owu.crm_programming_school.models.ResponseAccess;
import ua.com.owu.crm_programming_school.models.User;
import ua.com.owu.crm_programming_school.services.AdminService;
import ua.com.owu.crm_programming_school.views.Views;

@RestController
@Tag(name="admin")
@RequestMapping(value = "/api/v1/admin")
public class AdminController {
    AdminService adminService;

    public AdminController(@Qualifier("adminServiceImpl1")AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/users")
    @Operation(description = "Create new user",
            responses = { @ApiResponse(description = "CREATED", responseCode = "201",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class)))})
    @JsonView(value = Views.Level3.class)
    public ResponseEntity<User> registerManager(@RequestBody User user) {
        return adminService.registerManager(user);
    }


    @GetMapping("/users/{id}/re_token")
    @Operation(description = "get some token: eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBnbWFpb...",
            responses = { @ApiResponse(description = "success", responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseAccess.class)))})
    public ResponseEntity<ResponseAccess> requestToken(@PathVariable Integer id) {
        return adminService.requestToken(id);
    }
}
