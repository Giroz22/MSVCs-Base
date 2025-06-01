package com.waveghost.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class MsvcDiscoveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcDiscoveryApplication.class, args);
	}

}
