package com.ffozdemir.librarymanagement.entity.concretes.business;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false , unique = true)
	private String isbn;

	private int pageCount;

	@ManyToOne
	private Author author;

	@ManyToOne
	private Publisher publisher;

	private int publishDate;

	@ManyToOne
	private Category category;

	@Column(nullable = false)
	private boolean loanable = true;

	@Column(nullable = false)
	private String shelfCode;

	@Column(nullable = false)
	private boolean active = true;

	@Column(nullable = false)
	private boolean featured = false;

	@Column(nullable = false)
	private LocalDateTime createDate;

	@Column(nullable = false)
	private boolean builtIn = false;

	@PrePersist
	private void setCreateDate() {
		this.createDate = LocalDateTime.now();
	}


}
