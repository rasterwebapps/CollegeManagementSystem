package com.cms.dto;

import com.cms.model.enums.ProficiencyLevel;
import jakarta.validation.constraints.NotNull;

public record FacultyLabExpertiseRequest(
        @NotNull Long facultyId,
        @NotNull Long labTypeId,
        @NotNull ProficiencyLevel proficiencyLevel,
        Boolean certified
) {
}
