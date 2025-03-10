package com.ffozdemir.librarymanagement.entity.concretes.user;

import com.ffozdemir.librarymanagement.entity.enums.RoleType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private RoleType roleType;

	@Column(nullable = false)
	private String name;

}
