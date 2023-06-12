package com.boot_camp.Boot_Camp.middleware;

import com.boot_camp.Boot_Camp.model.domain.UtilStoreDomain;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<UtilStoreDomain> responseException(ResponseStatusException e) {
        String errorMessage = e.getMessage();
        int errorCode = e.getStatus().value();

        assert errorMessage != null;
        if (errorMessage.startsWith(errorCode + " ")) {
            errorMessage = errorMessage.substring((errorCode + " ").length());
        }

        return ResponseEntity.status(e.getStatus()).body(new UtilStoreDomain(errorCode, errorMessage));
    }
}
