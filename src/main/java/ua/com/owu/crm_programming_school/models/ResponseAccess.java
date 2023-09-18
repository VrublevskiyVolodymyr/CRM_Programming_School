package ua.com.owu.crm_programming_school.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseAccess {
    @Schema(description = "User's access token", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBnbWFpbC5jb20ifQ.wFLYK0YOPzYs2fNQb91d3vBwTdpYOOGOJUfda-B5RU8")
    private String access;
}
