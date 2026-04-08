package com.cms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record SemesterRequest(
        @NotNull Long academicYearId,
        @NotBlank String name,
        @NotNull @Positive Integer number,
        @NotNull LocalDate startDate,
        @NotNull LocalDate endDate
) {
}
