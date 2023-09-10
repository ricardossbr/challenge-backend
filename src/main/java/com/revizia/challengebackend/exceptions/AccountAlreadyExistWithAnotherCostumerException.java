package com.revizia.challengebackend.exceptions;

public class AccountAlreadyExistWithAnotherCostumerException extends Throwable {
    public AccountAlreadyExistWithAnotherCostumerException(){
        super("This account already exist with another costumer, please verify the correct number!");
    }
    public AccountAlreadyExistWithAnotherCostumerException(String message){
        super(message);
    }
}
