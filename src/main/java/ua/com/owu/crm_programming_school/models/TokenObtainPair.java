package ua.com.owu.crm_programming_school.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenObtainPair {
    @NotBlank(message = "username is a required field")
    @Email
    @Size(min = 1, max = 25)
    @Schema(description = "User's username", example = "John")
    private String username;

    @NotBlank(message = "password is a required field")
    @Size(min = 5, max = 128, message = "The password must be between 5 and 128 characters long.")
    @Pattern(regexp = "^(?=.*[a-zA-Zа-яА-ЯЇЁіІё])(?=.*\\d)(?=.*[@#$%^&+=!]).*$|^admin$", message = "The password must contain an upper and lower case letter, a number and a special character.")
    @Schema(description = "User's password", example = "Password123!")
    private String password;
}
