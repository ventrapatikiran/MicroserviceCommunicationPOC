package com.wavelabs.microservice.communication.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableZuulProxy
public class MicroserviceCommunicationEurekaApplication {
	public static void main(String[] args) {
		SpringApplication.run(MicroserviceCommunicationEurekaApplication.class, args);
	}
	
	@Bean
	public RestTemplate restTemplete(RestTemplateBuilder restTempleteBuilder) {
		return restTempleteBuilder.build();
	}
}
