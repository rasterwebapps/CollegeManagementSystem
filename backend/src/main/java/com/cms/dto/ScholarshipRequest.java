package com.cms.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ScholarshipRequest(
    @NotNull(message = "Student ID is required") Long studentId,
    @NotBlank(message = "Name is required") @Size(max = 200) String name,
    @NotNull(message = "Amount is required") BigDecimal amount,
    @NotBlank(message = "Scholarship type is required") @Size(max = 50) String scholarshipType,
    @NotNull(message = "Awarded date is required") LocalDate awardedDate,
    LocalDate expiryDate,
    String status,
    String remarks
) {}
