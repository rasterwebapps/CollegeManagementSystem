package com.cms.dto;

import com.cms.model.enums.CourseType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CourseRequest(
        @NotBlank String name,
        @NotBlank String code,
        @NotNull Long programId,
        @NotNull Long departmentId,
        @NotNull @Positive Integer credits,
        @NotNull Integer theoryHours,
        @NotNull Integer practicalHours,
        @NotNull CourseType courseType,
        @NotNull @Positive Integer semesterNumber,
        Boolean active
) {
}
