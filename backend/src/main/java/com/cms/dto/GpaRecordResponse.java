package com.cms.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record GpaRecordResponse(
    Long id, Long studentId, String studentName,
    Long semesterId, String semesterName,
    BigDecimal semesterGpa, BigDecimal cgpa,
    Integer totalCredits, Integer earnedCredits,
    BigDecimal labComponentGpa, LocalDate calculationDate, String status,
    Instant createdAt, Instant updatedAt
) {}
