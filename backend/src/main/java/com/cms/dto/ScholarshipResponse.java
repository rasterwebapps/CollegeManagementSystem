package com.cms.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record ScholarshipResponse(
    Long id, Long studentId, String studentName,
    String name, BigDecimal amount, String scholarshipType,
    LocalDate awardedDate, LocalDate expiryDate, String status, String remarks,
    Instant createdAt, Instant updatedAt
) {}
