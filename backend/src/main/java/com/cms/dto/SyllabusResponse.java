package com.cms.dto;

import java.time.Instant;

public record SyllabusResponse(
    Long id,
    Long courseId,
    String courseName,
    Long academicYearId,
    String academicYearName,
    String content,
    String objectives,
    String status,
    Instant createdAt,
    Instant updatedAt
) {}
