package ua.com.owu.crm_programming_school.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Response error details")
public class ResponseError {
    @Schema(description = "Error message", example = "invalid token")
    private String error;

    @Schema(description = "Error code", example = "401")
    private int code;
}