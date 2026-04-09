package com.cms.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProgramRequest(
    @NotBlank(message = "Name is required")
    @Size(max = 200, message = "Name must not exceed 200 characters")
    String name,

    @NotBlank(message = "Code is required")
    @Size(max = 20, message = "Code must not exceed 20 characters")
    String code,

    @NotNull(message = "Department ID is required")
    Long departmentId,

    @NotNull(message = "Duration years is required")
    @Min(value = 1, message = "Duration must be at least 1 year")
    Integer durationYears,

    @NotBlank(message = "Degree type is required")
    @Size(max = 50, message = "Degree type must not exceed 50 characters")
    String degreeType
) {}
