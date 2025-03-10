package com.ffozdemir.librarymanagement.payload.request.user;

import com.ffozdemir.librarymanagement.payload.request.abstracts.BaseUserRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CreateUserRequest extends BaseUserRequest {

    @NotNull(message = "Please enter role for the user")
    @Pattern(regexp = "ADMIN|STAFF|MEMBER", message = "Role must be one of the following: ADMIN, STAFF, MEMBER")
    private String roleType;

}
