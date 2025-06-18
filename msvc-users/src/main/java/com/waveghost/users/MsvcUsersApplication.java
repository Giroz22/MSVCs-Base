package com.waveghost.users;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import com.waveghost.users.infrastructure.enums.UserRole;
import com.waveghost.users.persistence.entitites.RoleEntity;
import com.waveghost.users.persistence.repositories.RoleRepository;
import com.waveghost.users.persistence.repositories.UserRepository;

@SpringBootApplication
@EnableDiscoveryClient
public class MsvcUsersApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcUsersApplication.class, args);
	}

	//IT CREATE THE ROLES
	@Bean
	CommandLineRunner init(UserRepository userRepository, RoleRepository roleRepository){
		return args ->{
			boolean hasRoles = roleRepository.count() > 0;

			if (!hasRoles) {
				roleRepository.save(new RoleEntity(null, UserRole.ROOT));
				roleRepository.save(new RoleEntity(null, UserRole.USER));
			}
		};
	}
}
