package com.waveghost.auth.infrastructure.errors;

public class BadCredentialsException extends RuntimeException
{
    public BadCredentialsException(String message){
        super(message);
    }
}
