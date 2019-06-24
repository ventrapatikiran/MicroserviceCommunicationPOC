package com.wavelabs.microservice.communication.eureka.fiegn.client;

import javax.websocket.server.PathParam;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name="helloservice")
public interface HelloClient {
	
	@RequestMapping(value = "/hello/{name}", method=RequestMethod.GET)
	String sayHello(@PathParam("name") String name);
	
	
	@RequestMapping(value="/histrix/fallback", method=RequestMethod.GET)
	String histrix() throws InterruptedException;
}
