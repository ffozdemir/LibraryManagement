package com.ffozdemir.librarymanagement.entity.concretes.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String lastName;

	@Column(nullable = false)
	private int score;

	@Column(nullable = false)
	private String address;

	@Column(nullable = false, unique = true)
	private String phone;

	private LocalDate birthDate;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private LocalDateTime createDate;

	@Column(nullable = false)
	private boolean builtIn = false;

	@ManyToOne
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Role role;

	@PrePersist
	private void setCreateDate() {
		this.createDate = LocalDateTime.now();
	}

}
