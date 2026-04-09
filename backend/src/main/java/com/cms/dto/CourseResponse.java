package com.cms.dto;

import java.time.Instant;

public record CourseResponse(
    Long id,
    String name,
    String code,
    Long programId,
    String programName,
    Integer credits,
    String courseType,
    Instant createdAt,
    Instant updatedAt
) {}
