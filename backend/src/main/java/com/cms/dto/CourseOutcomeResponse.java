package com.cms.dto;

import com.cms.model.enums.BloomLevel;

public record CourseOutcomeResponse(
        Long id,
        Long courseId,
        String courseName,
        String code,
        String description,
        BloomLevel bloomLevel
) {
}
