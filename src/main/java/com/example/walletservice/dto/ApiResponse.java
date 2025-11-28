package com.example.walletservice.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApiResponse<T> {
    private boolean success;
    private int statusCode;
    private String message;
    private T data;

    public ApiResponse() {}
    public ApiResponse(boolean success, int code, String message, T data) {
        this.success = success;
        this.statusCode = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> ok(String message, T data) {
        return new ApiResponse<>(true, 200, message, data);
    }

    public static <T> ApiResponse<T> error(int code, String message) {
        return new ApiResponse<>(false, code, message, null);
    }
}
