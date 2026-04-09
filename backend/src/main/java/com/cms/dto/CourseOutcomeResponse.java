package com.cms.dto;

import java.time.Instant;

public record CourseOutcomeResponse(
    Long id,
    Long syllabusId,
    String code,
    String description,
    String bloomLevel,
    Instant createdAt,
    Instant updatedAt
) {}
