package com.cms.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public record GpaRecordRequest(
    @NotNull(message = "Student ID is required") Long studentId,
    @NotNull(message = "Semester ID is required") Long semesterId,
    @NotNull(message = "Semester GPA is required") BigDecimal semesterGpa,
    @NotNull(message = "CGPA is required") BigDecimal cgpa,
    @NotNull(message = "Total credits is required") Integer totalCredits,
    @NotNull(message = "Earned credits is required") Integer earnedCredits,
    BigDecimal labComponentGpa,
    @NotNull(message = "Calculation date is required") LocalDate calculationDate,
    String status
) {}
