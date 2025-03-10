package com.ffozdemir.librarymanagement.payload.request.user;

import com.ffozdemir.librarymanagement.payload.request.abstracts.BaseUserRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
public class RegisterOrUpdateRequest extends BaseUserRequest {
}
