package com.cms.dto;

import java.time.Instant;
import java.time.LocalDate;

public record SemesterResponse(
    Long id,
    String name,
    Integer semesterNumber,
    Long academicYearId,
    String academicYearName,
    LocalDate startDate,
    LocalDate endDate,
    Instant createdAt,
    Instant updatedAt
) {}
