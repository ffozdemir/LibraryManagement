package com.ffozdemir.librarymanagement.payload.messages;

public class ErrorMessages {

    public static final String USER_NOT_FOUND_BY_EMAIL = "User not found with email: %s";
    public static final String ROLE_NOT_FOUND = "Role: %s not found";
    public static final String ROLE_ALREADY_EXISTS = "Role already exists";
    public static final String USER_ALREADY_EXISTS = "User already exists";
    public static final String INVALID_CREDENTIALS = "Invalid credentials";
    public static final String BOOK_NOT_FOUND = "Book not found";
    public static final String BOOK_ALREADY_EXISTS = "Book already exists";
    public static final String LOAN_NOT_FOUND = "Loan not found";
    public static final String LOAN_ALREADY_EXISTS = "Loan already exists";
    public static final String INVALID_LOAN_PERIOD = "Invalid loan period";
    public static final String EMAIL_ALREADY_EXISTS = "Email already exists for another user";
    public static final String PHONE_ALREADY_EXISTS = "Phone already exists for another user";
    public static final String VALIDATION_ERROR_MESSAGE = "Validation error";
    public static final String INTERNAL_SERVER_ERROR_MESSAGE = "Internal server error";
    public static final String USER_NOT_FOUND_BY_ID = "User not found with id: %s";
    public static final String ACCESS_DENIED_MESSAGE = "Access denied you do not have permission to access this resource";
    public static final String BAD_REQUEST_MESSAGE = "Bad request you have sent invalid data";
    public static final String USER_HAS_LOAN_MESSAGE = "User has an active loan, please return the book before deleting the user";
    public static final String PASSWORD_SHOULD_NOT_MATCHED = "Password should not be same as old password";
    public static final String BOOK_ISBN_ALREADY_EXISTS = "Book with this ISBN %s already exists";
    public static final String AUTHOR_NOT_FOUND_BY_ID = "Author not found with id %s";
    public static final String PUBLISHER_NOT_FOUND_BY_ID = "Publisher not found with id %s";
    public static final String CATEGORY_NOT_FOUND_BY_ID = "Category not found with id %s";

    private ErrorMessages() {
    }

}
