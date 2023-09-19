package ua.com.owu.crm_programming_school.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@Schema(description = "Response error details")
public class ResponseError {
    @Schema(description = "Error message", example = "Token is not valid")
    private String error;

    @Schema(description = "Error code", example = "403")
    private int code;
}