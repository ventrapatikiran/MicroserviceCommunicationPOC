package com.wavelabs.microservice.communication.eureka.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import com.wavelabs.microservice.communication.eureka.exceptions.UserDefinedCommunicationException;
import com.wavelabs.microservice.communication.eureka.fiegn.client.HelloClient;

@RestController
@RequestMapping("/communication")
public class CommunicationMicroServiceController {
	@Autowired
	DiscoveryClient discoveryClient;
	
	@Autowired
	EurekaClient eurekaClient;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	HelloClient helloClient;
	
	@Value("${service.helloserviceclinet.serviceId}")
	String helloCleintApplicationName;
	
	
	@RequestMapping(value = "/v1", method = RequestMethod.GET)
	public String withApplicationEurekaClient() {
		Application application = eurekaClient.getApplication("helloservice");
		InstanceInfo instanceInfo = application.getInstances().get(0);
		String url = "http://" + instanceInfo.getIPAddr() + ":" + instanceInfo.getPort() + "/"
				+ "sayHello/hello/kiran";
		System.out.println("URL" + url);
		String response = restTemplate.getForObject(url, String.class);
		return response;
	}
	
	@RequestMapping(value = "/v2", method = RequestMethod.GET)
	public String withDiscoveryClient() {
		//service name should be dynamic
		// because of some error hard coded it
		ServiceInstance serviceInstance = discoveryClient.getInstances("helloservice").get(0);
		String url = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/"
				+ "sayHello/hello/kiran";
		System.out.println("URL" + url);
		RestTemplate restTemplate = new RestTemplate();
		String response = restTemplate.getForObject(url, String.class);
		return response;
	}
	
	@RequestMapping(value = "/v3", method = RequestMethod.GET)
	public String withFeignClient() {
		String str =null;
		try {
		str = helloClient.sayHello("Kiran");
		}catch(Exception e) {
			//Handled Exception in spring boot
			throw new UserDefinedCommunicationException(e);
		}
		return str;
	}
	
	@RequestMapping("/service-instances/{applicationName}")
	public List<ServiceInstance> serviceInstancesByApplicationName(@PathVariable String applicationName) {
		return discoveryClient.getInstances(applicationName);
	}
}
