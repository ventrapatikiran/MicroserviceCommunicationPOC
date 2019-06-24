package com.wavelabs.microservice.communication.eureka.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommunicationExceptionController {
	
	@ExceptionHandler(value=UserDefinedCommunicationException.class)
	public ResponseEntity<Object> exception(){
	  return new ResponseEntity<>("Registry Service not found", HttpStatus.NOT_FOUND);
	}

}
