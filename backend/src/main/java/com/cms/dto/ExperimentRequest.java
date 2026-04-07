package com.cms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ExperimentRequest(
        @NotNull Long courseId,
        @NotBlank String name,
        String description,
        String objective,
        String procedureText,
        String preRequirements,
        Integer sequenceOrder
) {
}
