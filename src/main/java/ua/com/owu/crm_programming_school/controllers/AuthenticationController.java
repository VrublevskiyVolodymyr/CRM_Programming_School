package ua.com.owu.crm_programming_school.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ua.com.owu.crm_programming_school.models.*;
import ua.com.owu.crm_programming_school.services.AuthenticationService;

@RestController
@RequestMapping(value = "/auth")
@AllArgsConstructor
@Tag(name="auth")
public class AuthenticationController {
    private AuthenticationService authenticationService;


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
                                    schema = @Schema(implementation = AuthenticationResponse.class)))})
    public ResponseEntity<AuthenticationResponse> refresh(@RequestBody RequestRefresh requestRefresh) {

        return authenticationService.refresh(requestRefresh);
    }
}
