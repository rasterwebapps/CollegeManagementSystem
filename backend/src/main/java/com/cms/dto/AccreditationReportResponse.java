package com.cms.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record AccreditationReportResponse(
    Long id,
    Long departmentId,
    String departmentName,
    String reportType,
    String academicYear,
    LocalDate generatedDate,
    BigDecimal overallScore,
    String status,
    String summary,
    Instant createdAt,
    Instant updatedAt
) {}
