package com.project.java.pp.common.exceptions;

public class NotUniqueAccountException extends RuntimeException{
    public NotUniqueAccountException(String message){
        super(message);
    }
}