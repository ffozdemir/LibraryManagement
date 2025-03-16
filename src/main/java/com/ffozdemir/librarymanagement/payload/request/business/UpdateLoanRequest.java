package com.ffozdemir.librarymanagement.payload.request.business;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateLoanRequest {

    @NotBlank(message = "Please enter notes")
    @Pattern(regexp = "^[a-zA-Z0-9\\s]+$", message = "Notes can only contain letters, numbers, and spaces")
    private String notes;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Future(message = "Expire date must be in the future")
    private LocalDateTime expireDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @PastOrPresent(message = "Return date must be in the past or present")
    private LocalDateTime returnDate;

}
