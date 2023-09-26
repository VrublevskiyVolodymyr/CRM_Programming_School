package ua.com.owu.crm_programming_school.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ua.com.owu.crm_programming_school.models.ResponseAccess;
import ua.com.owu.crm_programming_school.models.ResponseError;
import ua.com.owu.crm_programming_school.models.UserRequest;
import ua.com.owu.crm_programming_school.models.UserResponse;
import ua.com.owu.crm_programming_school.services.adminService.AdminService;

@RestController
@Tag(name="admin")
@RequestMapping(value = "/admin")
public class AdminController {
    AdminService adminService;

    public AdminController(@Qualifier("adminServiceImpl1")AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/users")
    @Operation(description = "Create new user",
            responses = { @ApiResponse(description = "created", responseCode = "201",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
                    @ApiResponse(responseCode = "400", description = "duplicate email",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseError.class),
                                    examples = @ExampleObject(name = "errorResponse",
                                            value = "{\"error\": \"duplicate email\", \"code\": 400}")))})

    public ResponseEntity<UserResponse> registerManager(@RequestBody @Valid UserRequest userRequest, HttpServletResponse response) {
        return adminService.registerManager(userRequest, response);
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
