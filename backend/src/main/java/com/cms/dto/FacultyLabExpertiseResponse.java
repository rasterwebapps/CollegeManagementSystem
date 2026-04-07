package com.cms.dto;

import com.cms.model.enums.ProficiencyLevel;

public record FacultyLabExpertiseResponse(
        Long id,
        Long facultyId,
        String employeeId,
        Long labTypeId,
        String labTypeName,
        ProficiencyLevel proficiencyLevel,
        boolean certified
) {
}
