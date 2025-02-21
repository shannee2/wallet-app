package com.walletapp.dto.general;

public class ErrResponse {
    private boolean success;
    private int status;
    private String message;

    public ErrResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() { return status; }
    public String getMessage() { return message; }
    public boolean isSuccess() { return success; }
}
