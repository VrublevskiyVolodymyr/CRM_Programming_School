package ua.com.owu.crm_programming_school.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "`group`")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the group", readOnly = true)
    private int id;

    @Column(name = "name", nullable = false, length = 128)
    @NotBlank(message = "name is a required field")
    @Size(min = 4, max = 128)
    @Schema(description = "Group name", example = "Marketing Team")
    private String name;

    public Group(String name) {
        this.name = name;
    }
}
