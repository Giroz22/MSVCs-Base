package com.waveghost.auth.infrastructure.errors;

public class EmailAlreadyExistException extends RuntimeException
{

    private static String ERROR_MESSAGE = "Already exist a user with email: %s";

    public EmailAlreadyExistException(String email){
        super( String.format(ERROR_MESSAGE, email));
    }

}
