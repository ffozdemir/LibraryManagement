package com.ffozdemir.librarymanagement.payload.messages;

public class ErrorMessages {

    public static final String USER_NOT_FOUND = "User not found with email: %s";
    public static final String ROLE_NOT_FOUND = "Role: %s not found";
    public static final String ROLE_ALREADY_EXISTS = "Role already exists";
    public static final String USER_ALREADY_EXISTS = "User already exists";
    public static final String INVALID_CREDENTIALS = "Invalid credentials";
    public static final String BOOK_NOT_FOUND = "Book not found";
    public static final String BOOK_ALREADY_EXISTS = "Book already exists";
    public static final String LOAN_NOT_FOUND = "Loan not found";
    public static final String LOAN_ALREADY_EXISTS = "Loan already exists";
    public static final String INVALID_LOAN_PERIOD = "Invalid loan period";

    private ErrorMessages() {
    }

}
