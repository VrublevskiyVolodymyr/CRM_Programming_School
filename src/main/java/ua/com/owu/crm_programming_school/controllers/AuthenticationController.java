package ua.com.owu.crm_programming_school.controllers;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ua.com.owu.crm_programming_school.models.*;
import ua.com.owu.crm_programming_school.services.authenticationService.AuthenticationService;

import java.io.IOException;

@RestController
@RequestMapping(value = "/auth")
@Tag(name="auth")
public class AuthenticationController {
    private AuthenticationService authenticationService;


    public AuthenticationController(@Qualifier("authenticationServiceImpl1") AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/activate/{token}")
    @Operation(description = "Activate user",
            responses = {
                    @ApiResponse(
                            description = "success",
                            responseCode = "200"
                                    )})
    public ResponseEntity activate(@PathVariable String token,  @RequestBody @Valid Password password) {

        return authenticationService.activate(token,password);
    }

    @PostMapping()
    @Operation(description = "Takes a set of user credentials and returns an access and refresh JSON web\\n\" +\n" +
            "token pair to prove the authentication of those credentials.",
            responses = {
                    @ApiResponse(
                            description = "success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AuthenticationResponse.class
                                    )))})
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid TokenObtainPair tokenObtainPair) {

        return authenticationService.authenticate(tokenObtainPair);
    }

    @PostMapping("/refresh")
    @Operation(description = "Takes a refresh type JSON web token and returns an access type JSON web\\n\" +\n" +
            "//            \"token if the refresh token is valid.",
            responses = {
                    @ApiResponse(
                            description = "success",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AuthenticationResponse.class))),
                    @ApiResponse(responseCode = "403", description = "Token is not valid",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseError.class)))})
    public ResponseEntity<AuthenticationResponse> refresh(@RequestBody RequestRefresh requestRefresh, HttpServletResponse response) throws IOException {

        return authenticationService.refresh(requestRefresh,response);
    }
}
