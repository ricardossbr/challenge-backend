package com.revizia.challengebackend.exceptions;

public class BalanceIsUnchangingException extends Throwable {

    public BalanceIsUnchangingException(){
        super(" The Balance is unchanging with this option, " +
                "if necessary to change, there tree options: withdrawal, deposit or another account!");
    }
    public BalanceIsUnchangingException(String message){
        super(message);
    }
}
