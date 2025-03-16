package com.ffozdemir.librarymanagement.entity.concretes.business;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Author {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private boolean builtIn = false;

	@OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<Book> books;
}
