package ua.com.owu.crm_programming_school.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "comments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the comment", readOnly = true)
    private Long id;

    @Column(name = "comment", nullable = false, length = 255)
    @Size(min = 1, max = 255)
    @Schema(required = true, description = "Comment text", example = "This is a comment.")
    private String comment;

    @Column(name = "created_at")
    @Schema(description = "Comment creation date", readOnly = true)
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    @Schema(description = "The order associated with the comment")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "manager_id", referencedColumnName = "id")
    private User manager;
}
