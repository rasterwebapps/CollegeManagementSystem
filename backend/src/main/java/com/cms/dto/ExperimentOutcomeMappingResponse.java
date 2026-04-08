package com.cms.dto;

import com.cms.model.enums.MappingLevel;

public record ExperimentOutcomeMappingResponse(
        Long experimentId,
        String experimentName,
        Long courseOutcomeId,
        String courseOutcomeCode,
        MappingLevel mappingLevel
) {
}
