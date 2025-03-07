package com.ffozdemir.librarymanagement.security.config;

import com.ffozdemir.librarymanagement.security.jwt.JwtAuthenticationEntryPoint;
import com.ffozdemir.librarymanagement.security.jwt.JwtAuthenticationFilter;
import com.ffozdemir.librarymanagement.security.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

	private final UserDetailsServiceImpl userDetailsService;
	private final JwtAuthenticationEntryPoint unauthorizedHandler;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private static final String[] AUTH_WHITELIST = {"/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**", "/", "/index.html", "/images/**", "/css/**", "/js/**", "/auth/login", "/auth/register"};

	@Bean
	public SecurityFilterChain filterChain(
				HttpSecurity http) throws Exception {
		http.cors(AbstractHttpConfigurer::disable)
					.csrf(AbstractHttpConfigurer::disable)
					.exceptionHandling(exception->exception.authenticationEntryPoint(unauthorizedHandler))
					.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
					.authorizeHttpRequests(authorize->authorize.requestMatchers(AUTH_WHITELIST)
								                                  .permitAll()
								                                  .anyRequest()
								                                  .authenticated());

		http.authenticationProvider(authenticationProvider());
		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(
				AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
