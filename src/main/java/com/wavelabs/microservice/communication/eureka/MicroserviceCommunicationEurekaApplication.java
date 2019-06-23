package com.wavelabs.microservice.communication.eureka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class MicroserviceCommunicationEurekaApplication {

	@Autowired
	DiscoveryClient discoveryClient;
	@Autowired
	EurekaClient eurekaClient;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Value("${service.helloserviceclinet.serviceId}")
	String helloCleintId;
	
	public static void main(String[] args) {
		SpringApplication.run(MicroserviceCommunicationEurekaApplication.class, args);
	}
	
	@RefreshScope
	@RestController
	class CommunicationMicroServiceController {

		@RequestMapping(name = "/comunicateWithMicroService")
		public String commnucateWithMicroService() {
			System.out.println(helloCleintId);
			System.out.println(discoveryClient.getInstances(helloCleintId));
			ServiceInstance serviceInstance = discoveryClient.getInstances(helloCleintId).get(0);
			String url = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/"
					+ "sayHello/hello/kiran";
			System.out.println("URL" + url);
			RestTemplate restTemplate = new RestTemplate();
			String response = (String) restTemplate.getForObject(url, Object.class);
			return response;
		}

		@RequestMapping(name = "/comunicateWithMicro", method = RequestMethod.GET)
		public String commnucateWithMicroService1() {
			System.out.println(eurekaClient.getApplication(helloCleintId));

			Application application = eurekaClient.getApplication(helloCleintId);
			
			InstanceInfo instanceInfo = application.getInstances().get(0);
			String url = "http://" + instanceInfo.getIPAddr() + ":" + instanceInfo.getPort() + "/"
					+ "sayHello/hello/kiran";
			System.out.println("URL" + url);
			String response = (String) restTemplate.getForObject(url, Object.class);
			return response;
		}
	}
	
	@Bean
	public RestTemplate restTemplete(RestTemplateBuilder restTempleteBuilder) {
		return restTempleteBuilder.build();
	}
	
	
	

}
