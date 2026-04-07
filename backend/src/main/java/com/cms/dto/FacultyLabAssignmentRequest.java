package com.cms.dto;

import jakarta.validation.constraints.NotNull;

public record FacultyLabAssignmentRequest(
        @NotNull Long facultyId,
        @NotNull Long labId,
        @NotNull Long courseId,
        @NotNull Long academicYearId,
        @NotNull Long semesterId
) {
}
