package com.ffozdemir.librarymanagement.payload.request.user;

import com.ffozdemir.librarymanagement.payload.request.abstracts.BaseUserRequest;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "Please enter role for the user")
    private String roleType;

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
