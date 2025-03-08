package com.ffozdemir.librarymanagement.service.user;

import com.ffozdemir.librarymanagement.entity.concretes.user.User;
import com.ffozdemir.librarymanagement.repository.user.UserRepository;
import com.ffozdemir.librarymanagement.service.validator.UniquePropertyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final UniquePropertyValidator uniquePropertyValidator;

	public boolean existsByEmail(
				String adminEmail) {
		return userRepository.existsByEmail(adminEmail);
	}

	public User saveUser(
				User user) {
		uniquePropertyValidator.checkDuplication(user.getEmail(), user.getPhone());
		return userRepository.save(user);
	}
}
