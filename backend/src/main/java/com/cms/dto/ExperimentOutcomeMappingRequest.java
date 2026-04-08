package com.cms.dto;

import com.cms.model.enums.MappingLevel;
import jakarta.validation.constraints.NotNull;

public record ExperimentOutcomeMappingRequest(
        @NotNull Long experimentId,
        @NotNull Long courseOutcomeId,
        @NotNull MappingLevel mappingLevel
) {
}
