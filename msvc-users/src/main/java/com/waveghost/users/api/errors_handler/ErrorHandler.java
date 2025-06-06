package com.waveghost.users.api.errors_handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.waveghost.users.api.dtos.response.errors.ErrorResponse;
import com.waveghost.users.infrastructure.exceptions.NotFoundException;

@RestControllerAdvice
public class ErrorHandler {

    // @ResponseStatus(HttpStatus.BAD_REQUEST)
    // @ExceptionHandler({
    // })
    // public ResponseEntity<ErrorResponse> errorsHandler(Exception ex){
    //     return ResponseEntity.badRequest().body(
    //         ErrorResponse.builder()
    //         .code(HttpStatus.BAD_REQUEST.value())
    //         .status(HttpStatus.BAD_REQUEST.name())
    //         .message(ex.getMessage())
    //         .build()
    //     );
    // }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({
        NotFoundException.class
    })
    public ResponseEntity<ErrorResponse> UsernameNotFoundExceptionHandler(Exception ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            ErrorResponse.builder()
            .code(HttpStatus.NOT_FOUND.value())
            .status(HttpStatus.NOT_FOUND.name())
            .message(ex.getMessage())
            .build()
        );
    }
}
