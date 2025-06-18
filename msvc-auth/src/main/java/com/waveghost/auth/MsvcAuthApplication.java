package com.waveghost.auth;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.waveghost.auth.api.clients.UserClient;
import com.waveghost.auth.api.dtos.request.UserRequest;
import com.waveghost.auth.infrastructure.enums.UserRole;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class MsvcAuthApplication {

	@Value("${auth.root.email}")
	private String rootUser;

	@Value("${auth.root.password}")
	private String rootPassword;

	public static void main(String[] args) {
		SpringApplication.run(MsvcAuthApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UserClient userClien, PasswordEncoder passwordEncoder){
		//CREATE ROOT USER ONLY ENV
		return args -> {
			userClien.create(
				UserRequest.builder()
				.email(rootUser)
				.password(passwordEncoder.encode(rootPassword))
				.roles(List.of(UserRole.ROOT))
				.build());
		};
	}
}
