package com.trishul.exception;

public class ExpenseDoesNotExistsException extends Exception{
    public ExpenseDoesNotExistsException(String message){
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
