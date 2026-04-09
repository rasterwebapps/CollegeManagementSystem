package com.cms.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SyllabusRequest(
    @NotNull(message = "Course ID is required")
    Long courseId,

    @NotNull(message = "Academic year ID is required")
    Long academicYearId,

    String content,

    String objectives,

    @Size(max = 50, message = "Status must not exceed 50 characters")
    String status
) {}
