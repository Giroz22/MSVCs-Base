package com.waveghost.auth.api.error_handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waveghost.auth.api.dtos.response.errors.ErrorResponse;
import com.waveghost.auth.infrastructure.errors.BadCredentialsException;
import com.waveghost.auth.infrastructure.errors.EmailAlreadyExistException;
import com.waveghost.auth.infrastructure.errors.UsernameNotFoundException;

import feign.FeignException;

@RestControllerAdvice
public class ErrorsHandlerController {

    @Autowired
    private ObjectMapper objectMapper;
    
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
        UsernameNotFoundException.class,
        BadCredentialsException.class,
        EmailAlreadyExistException.class
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

    @ExceptionHandler({
        FeignException.NotFound.class
    })
    public ResponseEntity<ErrorResponse> feignExceptionHandler(FeignException.NotFound ex){
        try {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                objectMapper.readValue(ex.contentUTF8(), ErrorResponse.class) 
            );
        } catch (Exception e) {

            System.out.println("Internal error: " + e.getMessage());

            return ResponseEntity.badRequest().body(
                ErrorResponse.builder()
                .status(HttpStatus.valueOf(ex.status()).name())
                .code(ex.status())
                .message(ex.contentUTF8())
                .build()
            );
        }
    }
}
