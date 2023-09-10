package com.revizia.challengebackend.exceptions;

public class AccountDoesNotExistException extends Throwable {

    public AccountDoesNotExistException(){
        super("This account does not exist!");
    }
    public AccountDoesNotExistException(String message){
        super(message);
    }
}
