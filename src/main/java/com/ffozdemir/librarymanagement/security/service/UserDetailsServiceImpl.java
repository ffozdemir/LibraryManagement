package com.ffozdemir.librarymanagement.security.service;

import com.ffozdemir.librarymanagement.entity.concretes.user.User;
import com.ffozdemir.librarymanagement.service.helper.MethodHelper;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final MethodHelper methodHelper;

	/**
	 * This method is used to load user by email.
	 *
	 * @param email the email of the user
	 * @return UserDetailsImpl
	 * @throws UsernameNotFoundException if user not found
	 */

	@Override
	public UserDetails loadUserByUsername(
				String email) throws UsernameNotFoundException {
		User user = methodHelper.loadUserByEmail(email);
		return new UserDetailsImpl(user);
	}
}
