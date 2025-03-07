package com.ffozdemir.librarymanagement.config;

import com.ffozdemir.librarymanagement.entity.concretes.user.Role;
import com.ffozdemir.librarymanagement.entity.concretes.user.User;
import com.ffozdemir.librarymanagement.entity.enums.RoleType;
import com.ffozdemir.librarymanagement.exception.ResourceNotFoundException;
import com.ffozdemir.librarymanagement.payload.messages.ErrorMessages;
import com.ffozdemir.librarymanagement.repository.user.RoleRepository;
import com.ffozdemir.librarymanagement.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataInitializer {

	private final RoleRepository roleRepository;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Value("${admin.password}")
	private String adminPassword;

	@Value("${admin.email}")
	private String adminEmail;

	@Value("${admin.phoneNumber}")
	private String adminPhoneNumber;

	@Value("${admin.address}")
	private String adminAddress;

	@Value("${admin.firstName}")
	private String adminFirstName;

	@Value("${admin.lastName}")
	private String adminLastName;

	@Value("${admin.birthDate}")
	private String adminBirthDate;

	/**
	 * This method is called when the application is ready. It initializes the database with default roles and an admin user if they do not exist
	 *
	 * @EventListener(ApplicationReadyEvent.class) is used to listen for the ApplicationReadyEvent, which is published when the application is fully started
	 */

	@EventListener(ApplicationReadyEvent.class)
	public void initializeData() {
		createDefaultRoles();
		createAdminUser();
	}

	public void createDefaultRoles() {
		if (!roleRepository.existsByRoleType(RoleType.ADMIN) && !roleRepository.existsByRoleType(RoleType.STAFF) && !roleRepository.existsByRoleType(RoleType.MEMBER)) {
			roleRepository.save(Role.builder()
						                    .roleType(RoleType.ADMIN)
						                    .name(RoleType.ADMIN.getName())
						                    .build());
			roleRepository.save(Role.builder()
						                    .roleType(RoleType.STAFF)
						                    .name(RoleType.STAFF.getName())
						                    .build());
			roleRepository.save(Role.builder()
						                    .roleType(RoleType.MEMBER)
						                    .name(RoleType.MEMBER.getName())
						                    .build());
		}
	}

	public void createAdminUser() {
		if (!userRepository.existsByEmail(adminEmail)) {
			User adminUser = User.builder()
						                 .firstName(adminFirstName)
						                 .lastName(adminLastName)
						                 .score(0)
						                 .address(adminAddress)
						                 .phone(adminPhoneNumber)
						                 .birthDate(LocalDate.parse(adminBirthDate))
						                 .email(adminEmail)
						                 .password(passwordEncoder.encode(adminPassword))
						                 .role(roleRepository.findByRoleType(RoleType.ADMIN)
									                       .orElseThrow(()->new ResourceNotFoundException(String.format(ErrorMessages.ROLE_NOT_FOUND, RoleType.ADMIN))))
						                 .builtIn(true)
						                 .build();
			userRepository.save(adminUser);
		}
	}
}
