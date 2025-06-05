package com.waveghost.auth.infrastructure.errors;

public class UsernameNotFoundException extends RuntimeException
{
    public UsernameNotFoundException(String message){
        super(message);
    }
}
