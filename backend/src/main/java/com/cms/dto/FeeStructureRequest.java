package com.cms.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record FeeStructureRequest(
    @NotNull(message = "Program ID is required") Long programId,
    @NotNull(message = "Semester ID is required") Long semesterId,
    @NotBlank(message = "Fee type is required") @Size(max = 50) String feeType,
    @NotNull(message = "Amount is required") BigDecimal amount,
    @Size(max = 10) String currency,
    @NotNull(message = "Effective from date is required") LocalDate effectiveFrom,
    LocalDate effectiveTo,
    String status
) {}
