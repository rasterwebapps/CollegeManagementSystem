package com.cms.dto;

import com.cms.model.enums.DegreeType;

public record ProgramResponse(
        Long id,
        String name,
        String code,
        Long departmentId,
        String departmentName,
        DegreeType degreeType,
        int durationYears,
        Integer totalCredits,
        boolean active
) {
}
