package com.cms.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PaymentRequest(
    @NotNull(message = "Student ID is required") Long studentId,
    Long feeStructureId,
    @NotNull(message = "Amount is required") BigDecimal amount,
    @NotNull(message = "Payment date is required") LocalDate paymentDate,
    @NotBlank(message = "Payment method is required") @Size(max = 50) String paymentMethod,
    @Size(max = 100) String transactionReference,
    String status,
    String remarks
) {}
