package com.cms.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CourseRequest(
    @NotBlank(message = "Name is required")
    @Size(max = 200, message = "Name must not exceed 200 characters")
    String name,

    @NotBlank(message = "Code is required")
    @Size(max = 20, message = "Code must not exceed 20 characters")
    String code,

    @NotNull(message = "Program ID is required")
    Long programId,

    @NotNull(message = "Credits is required")
    @Min(value = 1, message = "Credits must be at least 1")
    Integer credits,

    @NotBlank(message = "Course type is required")
    @Size(max = 50, message = "Course type must not exceed 50 characters")
    String courseType
) {}
