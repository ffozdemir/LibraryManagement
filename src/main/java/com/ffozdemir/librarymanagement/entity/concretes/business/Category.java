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
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private boolean builtIn = false;

	@Column(nullable = false)
	private int sequence;

	@OneToMany(mappedBy = "category")
	private LinkedHashSet<Book> books;

}
