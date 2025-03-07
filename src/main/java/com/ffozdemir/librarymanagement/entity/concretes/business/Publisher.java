package com.ffozdemir.librarymanagement.entity.concretes.business;

import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Publisher {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private boolean builtIn = false;

	@OneToMany(mappedBy = "publisher")
	private LinkedHashSet<Book> books;

}
