package com.cms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CourseOutcomeRequest(
    @NotNull(message = "Syllabus ID is required")
    Long syllabusId,

    @NotBlank(message = "Code is required")
    @Size(max = 20, message = "Code must not exceed 20 characters")
    String code,

    @NotBlank(message = "Description is required")
    String description,

    @Size(max = 50, message = "Bloom level must not exceed 50 characters")
    String bloomLevel
) {}
