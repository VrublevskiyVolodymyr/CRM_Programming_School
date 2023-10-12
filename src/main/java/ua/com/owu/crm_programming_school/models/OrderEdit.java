package ua.com.owu.crm_programming_school.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEdit {

    @Pattern(regexp = "^[a-zA-Zа-яА-ЯЇЁіІё]+$")
    @Size(min = 1, max = 25)
    @Nullable
    @Schema(description = "User's first name", example = "John")
    private String name;

    @Pattern(regexp = "^[a-zA-Zа-яА-ЯЇЁіІё]+$")
    @Size(min = 1, max = 25)
    @Nullable
    @Schema(description = "User's last name", example = "Doe")
    private String surname;

    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$")
    @Size(min = 1, max = 100)
    @Nullable
    @Schema(description = "User's email address", example = "john.doe@example.com")
    private String email;

    @Pattern(regexp = "^([+]?[\\s0-9]+)?(\\d{3}|[(]?[0-9]+[)])?([-]?[\\s]?[0-9])+$")
    @Size(min = 1, max = 12)
    @Nullable
    @Schema(description = "User's email address", example = "john.doe@example.com")
    private String phone;

    @Min(16)
    @Max(90)
    @Nullable
    @Schema(description = "User's age", example = "25")
    private Integer age;

    @Pattern(regexp = "FS|QACX|JCX|JSCX|FE|PCX")
    @Nullable
    @Schema(description = "User's course", example = "QACX")
    private String course;

    @Pattern(regexp = "static|online|^$")
    @Nullable
    @Schema(description = "User's course format", example = "online")
    private String courseFormat;

    @Pattern(regexp = "pro|minimal|premium|incubator|vip")
    @Nullable
    @Schema(description = "User's course type", example = "pro")
    private String courseType;

    @Min(1)
    @Max(2147483647)
    @Nullable
    @Schema(description = "Amount already paid by the user", example = "500")
    private Integer alreadyPaid;

    @Min(1)
    @Max(2147483647)
    @Nullable
    @Schema(description = "Total amount for course payment", example = "1000")
    private Integer sum;

    @Nullable
    @Schema(description = "User's message", example = "Thank you for registration!")
    private String msg;

    @Pattern(regexp = "In work|New|Agree|Disagree|Dubbing")
    @Nullable
    @Schema(description = "Order status", example = "New")
    private String status;

    @Column(name = "utm", nullable = true)
    @Nullable
    @Schema(description = "Order's UTM parameter", example = "utm_source=google&utm_medium=cpc")
    private String utm;

    @Nullable
    private String group;
}
