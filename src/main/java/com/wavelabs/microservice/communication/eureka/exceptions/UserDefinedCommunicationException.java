package com.wavelabs.microservice.communication.eureka.exceptions;

public class UserDefinedCommunicationException extends RuntimeException {
	
	public UserDefinedCommunicationException(Throwable e) {
		super(e);
	}

}
