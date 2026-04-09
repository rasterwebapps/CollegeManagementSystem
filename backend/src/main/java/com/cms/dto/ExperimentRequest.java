package com.cms.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ExperimentRequest(
    @NotNull(message = "Syllabus ID is required")
    Long syllabusId,

    @NotNull(message = "Experiment number is required")
    @Min(value = 1, message = "Experiment number must be at least 1")
    Integer experimentNumber,

    @NotBlank(message = "Title is required")
    @Size(max = 300, message = "Title must not exceed 300 characters")
    String title,

    String description,

    Long labTypeId,

    @Min(value = 1, message = "Duration hours must be at least 1")
    Integer durationHours
) {}
