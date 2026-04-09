package com.cms.dto;

import jakarta.validation.constraints.NotNull;

public record ExperimentOutcomeMappingRequest(
    @NotNull(message = "Experiment ID is required")
    Long experimentId,

    @NotNull(message = "Course outcome ID is required")
    Long courseOutcomeId
) {}
