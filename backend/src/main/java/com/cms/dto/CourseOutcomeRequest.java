package com.cms.dto;

import com.cms.model.enums.BloomLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CourseOutcomeRequest(
        @NotNull Long courseId,
        @NotBlank String code,
        @NotBlank String description,
        @NotNull BloomLevel bloomLevel
) {
}
