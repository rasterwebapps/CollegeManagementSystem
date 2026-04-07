package com.cms.dto;

public record ExperimentResponse(
        Long id,
        Long courseId,
        String courseName,
        String name,
        String description,
        String objective,
        String procedureText,
        String preRequirements,
        int sequenceOrder
) {
}
