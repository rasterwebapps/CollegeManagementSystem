package com.cms.dto;

import java.time.Instant;

public record ExperimentOutcomeMappingResponse(
    Long id,
    Long experimentId,
    String experimentTitle,
    Long courseOutcomeId,
    String courseOutcomeCode,
    Instant createdAt
) {}
