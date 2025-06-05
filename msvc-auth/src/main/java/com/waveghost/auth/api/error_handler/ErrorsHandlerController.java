package com.waveghost.auth.api.error_handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.waveghost.auth.api.dtos.response.errors.ErrorResponse;
import com.waveghost.auth.infrastructure.errors.BadCredentialsException;
import com.waveghost.auth.infrastructure.errors.UsernameNotFoundException;

@RestControllerAdvice
public class ErrorsHandlerController {
    
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
        UsernameNotFoundException.class,
        BadCredentialsException.class
    })
    public ResponseEntity<ErrorResponse> badRequestErrorHandler(Exception e){
        return ResponseEntity.badRequest().body(
            ErrorResponse.builder()
            .status(HttpStatus.BAD_REQUEST.name())
            .code(HttpStatus.BAD_REQUEST.value())
            .message(e.getMessage())
            .build()
        );
    }
}
