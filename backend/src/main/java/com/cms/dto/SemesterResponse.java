package com.cms.dto;

import java.time.LocalDate;

public record SemesterResponse(
        Long id,
        Long academicYearId,
        String academicYearName,
        String name,
        int number,
        LocalDate startDate,
        LocalDate endDate
) {
}
