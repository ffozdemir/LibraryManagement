package com.ffozdemir.librarymanagement.config;

import com.ffozdemir.librarymanagement.entity.concretes.user.Role;
import com.ffozdemir.librarymanagement.entity.concretes.user.User;
import com.ffozdemir.librarymanagement.entity.enums.RoleType;
import com.ffozdemir.librarymanagement.service.user.RoleService;
import com.ffozdemir.librarymanagement.service.user.UserService;
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

	private final UserService userService;
	private final RoleService roleService;
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
		roleService.saveRole(Role.builder()
					                     .roleType(RoleType.ADMIN)
					                     .name(RoleType.ADMIN.getName())
					                     .build());
		roleService.saveRole(Role.builder()
					                     .roleType(RoleType.STAFF)
					                     .name(RoleType.STAFF.getName())
					                     .build());
		roleService.saveRole(Role.builder()
					                     .roleType(RoleType.MEMBER)
					                     .name(RoleType.MEMBER.getName())
					                     .build());

	}

	public void createAdminUser() {
		userService.saveUser(User.builder()
					                     .firstName(adminFirstName)
					                     .lastName(adminLastName)
					                     .score(0)
					                     .address(adminAddress)
					                     .phone(adminPhoneNumber)
					                     .birthDate(LocalDate.parse(adminBirthDate))
					                     .email(adminEmail)
					                     .password(passwordEncoder.encode(adminPassword))
					                     .role(roleService.getRoleByName(RoleType.ADMIN))
					                     .builtIn(true)
					                     .build());
	}
}
