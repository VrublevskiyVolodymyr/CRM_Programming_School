package ua.com.owu.crm_programming_school.models;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
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
public class UserRequest {

    @NotBlank(message = "name is a required field")
    @Pattern(regexp = "^[a-zA-Zа-яА-ЯЇЁіІё]+$")
    @Size(min = 1, max = 20)
    @Schema(description = "User's first name", example = "John")
    private String name;

    @NotBlank(message = "name is a required field")
    @Pattern(regexp = "^[a-zA-Zа-яА-ЯЇЁіІё]+$")
    @Size(min = 1, max = 20)
    @Schema(description = "User's last name", example = "Doe")
    private String surname;

    @NotBlank(message = "email is a required field")
    @Email
    @Size(min = 1, max = 254)
    @Schema(description = "User's email address", example = "john.doe@example.com")
    private String email;
}
