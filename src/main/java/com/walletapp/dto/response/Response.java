package com.walletapp.dto.response;

public class Response {
    final boolean success;
    final String message;

    public Response(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
