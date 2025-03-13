package com.ffozdemir.librarymanagement.payload.response.business;

import com.ffozdemir.librarymanagement.payload.response.abstracts.BaseLoanResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class LoanResponseForAdminAndStaff extends BaseLoanResponse {
    private String notes;
}
