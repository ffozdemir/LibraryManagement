package com.ffozdemir.librarymanagement.entity.concretes.business;

import com.ffozdemir.librarymanagement.entity.concretes.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Loan {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private User user;

	@ManyToOne
	private Book book;

	@Column(nullable = false)
	private LocalDateTime loanDate;

	@Column(nullable = false)
	private LocalDateTime expireDate;

	private LocalDateTime returnDate;

	private String notes;

}
