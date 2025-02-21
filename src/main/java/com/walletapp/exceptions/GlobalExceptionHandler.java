package com.walletapp.exceptions;
import com.walletapp.dto.general.ErrResponse;
import com.walletapp.exceptions.auth.UserIdAuthenticationFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrResponse> handleAccessDeniedException(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ErrResponse(403, ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrResponse> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrResponse(500, "Internal server error: " + ex.getMessage()));
    }

    @ExceptionHandler(UserIdAuthenticationFailedException.class)
    public ResponseEntity<ErrResponse> handleUserIdAuthFailException(UserIdAuthenticationFailedException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrResponse(403, "han bhai"+ex.getMessage()));
    }
}
