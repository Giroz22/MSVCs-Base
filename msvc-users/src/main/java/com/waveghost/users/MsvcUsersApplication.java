package com.waveghost.users;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.waveghost.users.infrastructure.enums.UserRole;
import com.waveghost.users.persistence.entitites.RoleEntity;
import com.waveghost.users.persistence.entitites.UserEntity;
import com.waveghost.users.persistence.repositories.RoleRepository;
import com.waveghost.users.persistence.repositories.UserRepository;

@SpringBootApplication
@EnableDiscoveryClient
public class MsvcUsersApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcUsersApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UserRepository userRepository, RoleRepository roleRepository){
		return args ->{
			boolean hasRoles = roleRepository.count() > 0;
			boolean hasRoot = userRepository.existsByEmail("giraldosernaalejandro@gmail.com");

			if (!hasRoles) {
				RoleEntity rootRole = roleRepository.save(new RoleEntity(null, UserRole.ROOT));
				RoleEntity userRole = roleRepository.save(new RoleEntity(null, UserRole.USER));

				if (!hasRoot) {
					userRepository.save(new UserEntity(
						null, 
						"giraldosernaalejandro@gmail.com",
						new BCryptPasswordEncoder().encode("QPC9RMDa"),
						Set.of(rootRole, userRole)
					));
				}
			}
		};
	}
}
