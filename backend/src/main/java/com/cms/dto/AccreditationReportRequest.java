package com.cms.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AccreditationReportRequest(
    @NotNull(message = "Department ID is required") Long departmentId,
    @NotBlank(message = "Report type is required") @Size(max = 50) String reportType,
    @NotBlank(message = "Academic year is required") @Size(max = 20) String academicYear,
    @NotNull(message = "Generated date is required") LocalDate generatedDate,
    BigDecimal overallScore,
    String status,
    String summary
) {}
