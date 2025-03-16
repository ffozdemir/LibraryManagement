package com.ffozdemir.librarymanagement.payload.messages;

public class ErrorMessages {

    public static final String USER_NOT_FOUND_BY_EMAIL = "User not found with email: %s";
    public static final String ROLE_NOT_FOUND = "Role: %s not found";
    public static final String INVALID_CREDENTIALS = "Invalid credentials";
    public static final String EMAIL_ALREADY_EXISTS = "Email already exists for another user";
    public static final String PHONE_ALREADY_EXISTS = "Phone already exists for another user";
    public static final String VALIDATION_ERROR_MESSAGE = "Validation error";
    public static final String INTERNAL_SERVER_ERROR_MESSAGE = "Internal server error";
    public static final String USER_NOT_FOUND_BY_ID = "User not found with id: %s";
    public static final String ACCESS_DENIED_MESSAGE = "Access denied you do not have permission to access this resource";
    public static final String USER_CAN_NOT_BE_DELETED_HAS_LOAN_MESSAGE = "User has an active loan, please return the" +
            " " +
            "book before deleting the user";
    public static final String USER_HAS_LOAN_AND_NOT_RETURNED_BOOK = "User has an active loan and not returned the " +
            "book, please return the book before borrowing another book";
    public static final String PASSWORD_SHOULD_NOT_MATCHED = "Password should not be same as old password";
    public static final String BOOK_ISBN_ALREADY_EXISTS = "Book with this ISBN %s already exists";
    public static final String AUTHOR_NOT_FOUND_BY_ID = "Author not found with id %s";
    public static final String PUBLISHER_NOT_FOUND_BY_ID = "Publisher not found with id %s";
    public static final String CATEGORY_NOT_FOUND_BY_ID = "Category not found with id %s";
    public static final String PUBLISHER_ALREADY_EXISTS_WITH_NAME = "Publisher already exists with name %s";
    public static final String PUBLISHER_NOT_DELETABLE_WITH_BOOKS = "Publisher not deletable, there are books associated with this publisher";
    public static final String CATEGORY_ALREADY_EXISTS_WITH_NAME = "Category already exists with name %s";
    public static final String CATEGORY_NOT_DELETABLE_WITH_BOOKS = "Category not deletable, there are books associated with this category";
    public static final String AUTHOR_ALREADY_EXISTS_WITH_NAME = "Author already exists with name %s";
    public static final String AUTHOR_NOT_DELETABLE_WITH_BOOKS = "Author not deletable, there are books associated with this author";
    public static final String BOOK_NOT_FOUND_BY_ID = "Book not found with id %s";
    public static final String BOOK_CANNOT_BE_DELETED = "Book cannot be deleted, it is built-in or someone has borrowed it";
    public static final String INVALID_PUBLISH_DATE = "Publish date must be in a valid date format like yyyy(1995)";
    public static final String BOOK_SEARCH_PARAMETERS_NOT_PROVIDED = "At least one book search paramater should be given";
    public static final String LOAN_NOT_FOUND_BY_ID = "Loan not found with id %s";
    public static final String LOAN_NOT_BELONG_TO_USER = "Loan does not belong to user with id %s";
    public static final String BOOK_NOT_AVAILABLE = "Book with id %s is not available for loan";
    public static final String USER_BORROW_LIMIT_EXCEEDED = "User borrow limit exceeded";
    public static final String INVALID_ROLE_TYPE = "Role type is not valid : %s";

    private ErrorMessages() {
    }

}
