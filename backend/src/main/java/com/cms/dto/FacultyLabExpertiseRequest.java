package com.cms.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record FacultyLabExpertiseRequest(
    @NotNull(message = "Faculty ID is required")
    Long facultyId,

    @NotNull(message = "Lab type ID is required")
    Long labTypeId,

    @NotBlank(message = "Proficiency level is required")
    @Size(max = 50, message = "Proficiency level must not exceed 50 characters")
    String proficiencyLevel,

    LocalDate certifiedDate
) {}
