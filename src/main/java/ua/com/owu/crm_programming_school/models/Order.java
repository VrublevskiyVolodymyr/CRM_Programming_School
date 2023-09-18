package ua.com.owu.crm_programming_school.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the order", readOnly = true)
    private Long id;

    @Column(name = "name", nullable = true, length = 25)
    @Pattern(regexp = "^[a-zA-Zа-яА-ЯЇЁіІё]+$")
    @Size(min = 1, max = 25)
    @Schema(description = "User's first name", example = "John")
    private String name;

    @Column(name = "surname", nullable = true, length = 25)
    @Pattern(regexp = "^[a-zA-Zа-яА-ЯЇЁіІё]+$")
    @Size(min = 1, max = 25)
    @Schema(description = "User's last name", example = "Doe")
    private String surname;

    @Column(name = "email", nullable = true, unique = true, length = 100)
    @Email
    @Size(min = 1, max = 100)
    @Schema(description = "User's email address", example = "john.doe@example.com")
    private String email;

    @Column(name = "phone", nullable = true, length = 12)
    @Pattern(regexp = "^([+]?[\\s0-9]+)?(\\d{3}|[(]?[0-9]+[)])?([-]?[\\s]?[0-9])+$")
    @Size(min = 1, max = 12)
    @Schema(description = "User's email address", example = "john.doe@example.com")
    private String phone;

    @Column(name = "age", nullable = true)
    @Min(16)
    @Max(90)
    @Schema(description = "User's age", example = "25")
    private Integer age;

    @Column(name = "course", nullable = true)
    @Pattern(regexp = "FS|QACX|JCX|JSCX|FE|PCX")
    @Schema(description = "User's course", example = "QACX")
    private String course;

    @Column(name = "course_format", nullable = true)
    @Pattern(regexp = "static|online|^$")
    @Schema(description = "User's course format", example = "online")
private String courseFormat;

    @Column(name = "course_type", nullable = true)
    @Pattern(regexp = "pro|minimal|premium|incubator|vip")
    @Schema(description = "User's course type", example = "pro")
    private String courseType;

    @Column(name = "alreadyPaid", nullable = true)
    @Min(1)
    @Max(2147483647)
    @Schema(description = "Amount already paid by the user", example = "500")
    private Integer alreadyPaid;

    @Column(name = "sum", nullable = true)
    @Min(1)
    @Max(2147483647)
    @Schema(description = "Total amount for course payment", example = "1000")
    private Integer sum;

    @Column(name = "msg", nullable = true)
    @Schema(description = "User's message", example = "Thank you for registration!")
    private String msg;

    @Column(name = "status", nullable = true)
    @Pattern(regexp = "In work|New|Aggre|Disaggre|Dubbing")
    @Schema(description = "Order status", example = "New")
    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User manager;

    @Column(name = "created_at", nullable = true)
    @Schema(description = "Order creation date")
    private LocalDateTime created;

    @Column(name = "utm", nullable = true)
    @Schema(description = "Order's UTM parameter", example = "utm_source=google&utm_medium=cpc")
    private String utm;

    @OneToMany(mappedBy = "order")
    private List<Comment> comments;

    @ManyToOne
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    private Group group;
}
