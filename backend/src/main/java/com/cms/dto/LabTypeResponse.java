package com.cms.dto;

import java.time.Instant;

public record LabTypeResponse(
    Long id,
    String name,
    String description,
    Instant createdAt,
    Instant updatedAt
) {}
