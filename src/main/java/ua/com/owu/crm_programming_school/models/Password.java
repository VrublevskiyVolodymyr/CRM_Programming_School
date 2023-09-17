package ua.com.owu.crm_programming_school.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Password {
    @NotBlank(message = "password is a required field")
    @Size(min = 8, max = 128, message = "The password must be between 8 and 20 characters long.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).*$", message = "The password must contain an upper and lower case letter, a number and a special character.")
    private String password;
}
