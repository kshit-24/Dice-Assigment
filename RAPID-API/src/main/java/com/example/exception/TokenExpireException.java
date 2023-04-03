package com.example.exception;

public class TokenExpireException extends RuntimeException {
 
	private static final long serialVersionUID = 1L;
    private String message;
 
    public TokenExpireException()  {}
 
    public TokenExpireException(String msg)
    {
        super(msg);
        this.message = msg;
    }
}