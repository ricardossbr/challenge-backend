package com.revizia.challengebackend.exceptions;

public class CostumerDoesNotExistException extends Throwable {

    public CostumerDoesNotExistException(){
        super("This costumer does not exist!");
    }
    public CostumerDoesNotExistException(String message){
        super(message);
    }
}
