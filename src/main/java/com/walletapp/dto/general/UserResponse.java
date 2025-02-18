package com.walletapp.dto.general;

public class UserResponse {
    private final boolean success;
    private final int status;
    private final String message;
    private final String token;

    public UserResponse(boolean success, int status, String message, String token) {
        this.success = success;
        this.status = status;
        this.message = message;
        this.token = token;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }

    public boolean isSuccess() {
        return success;
    }
}
