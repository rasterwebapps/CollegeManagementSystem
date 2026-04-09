package com.cms.dto;

import java.time.Instant;

public record ProgramResponse(
    Long id,
    String name,
    String code,
    Long departmentId,
    String departmentName,
    Integer durationYears,
    String degreeType,
    Instant createdAt,
    Instant updatedAt
) {}
