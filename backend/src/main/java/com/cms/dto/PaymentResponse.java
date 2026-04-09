package com.cms.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record PaymentResponse(
    Long id, Long studentId, String studentName,
    Long feeStructureId, BigDecimal amount,
    LocalDate paymentDate, String paymentMethod,
    String transactionReference, String status, String remarks,
    Instant createdAt, Instant updatedAt
) {}
