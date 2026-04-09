package com.cms.dto;

import java.time.Instant;

public record ExperimentResponse(
    Long id,
    Long syllabusId,
    Integer experimentNumber,
    String title,
    String description,
    Long labTypeId,
    String labTypeName,
    Integer durationHours,
    Instant createdAt,
    Instant updatedAt
) {}
