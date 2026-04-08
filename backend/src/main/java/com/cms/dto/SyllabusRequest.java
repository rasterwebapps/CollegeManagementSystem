package com.cms.dto;

import jakarta.validation.constraints.NotNull;

public record SyllabusRequest(
        @NotNull Long courseId,
        @NotNull Long academicYearId,
        String content,
        Integer version,
        Boolean approved
) {
}
