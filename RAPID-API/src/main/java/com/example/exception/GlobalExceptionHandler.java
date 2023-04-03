package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.demo.config.JwtAuthFilter;
import com.example.demo.controller.JwtController;

@RestControllerAdvice
public class GlobalExceptionHandler extends ExceptionHandlerExceptionResolver {

	@ExceptionHandler(TokenExpireException.class)
	public ResponseEntity<ErrorResponse>  handleTokenExpireException(TokenExpireException ex)
	{
		 ErrorResponse message = new ErrorResponse(
			        HttpStatus.NOT_FOUND.value(),
			        ex.getMessage());
		 
		 return new ResponseEntity<ErrorResponse>(message, HttpStatus.NOT_FOUND);
	}
}