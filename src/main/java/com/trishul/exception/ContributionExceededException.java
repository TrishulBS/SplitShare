package com.trishul.exception;

public class ContributionExceededException extends Exception{
    public ContributionExceededException(String message){
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

}
