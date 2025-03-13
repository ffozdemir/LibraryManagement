package com.ffozdemir.librarymanagement.payload.response.business;

import com.ffozdemir.librarymanagement.payload.response.abstracts.BaseLoanResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@SuperBuilder
public class LoanResponseForAdminAndStaff extends BaseLoanResponse {
    private String notes;
}
