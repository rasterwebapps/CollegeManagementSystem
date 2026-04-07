package com.cms.dto;

public record SyllabusResponse(
        Long id,
        Long courseId,
        String courseName,
        Long academicYearId,
        String academicYearName,
        String content,
        int version,
        boolean approved
) {
}
