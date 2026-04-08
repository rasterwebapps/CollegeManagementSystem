package com.cms.dto;

import com.cms.model.enums.DegreeType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProgramRequest(
        @NotBlank String name,
        @NotBlank String code,
        @NotNull Long departmentId,
        @NotNull DegreeType degreeType,
        @NotNull @Positive Integer durationYears,
        Integer totalCredits,
        Boolean active
) {
}
