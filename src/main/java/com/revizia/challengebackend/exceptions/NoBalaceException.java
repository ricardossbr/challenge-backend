package com.revizia.challengebackend.exceptions;

public class NoBalaceException extends Throwable {

    public NoBalaceException(){
        super("There no money in your account, to do this operation!");
    }
    public NoBalaceException(String message){
        super(message);
    }
}
