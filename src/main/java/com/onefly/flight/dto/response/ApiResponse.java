package com.onefly.flight.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Unified API Response")
public class ApiResponse<T> {

    @Schema(description = "Status code: 0 for success, other for failure")
    private int status;

    @Schema(description = "Response message")
    private String msg;

    @Schema(description = "Response data")
    private T data;

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .status(0)
                .msg("success")
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> success(String msg, T data) {
        return ApiResponse.<T>builder()
                .status(0)
                .msg(msg)
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> error(int status, String msg) {
        return ApiResponse.<T>builder()
                .status(status)
                .msg(msg)
                .build();
    }
}
