package com.cms.dto;

import java.time.Instant;

public record DepartmentResponse(
    Long id,
    String name,
    String code,
    Instant createdAt,
    Instant updatedAt
) {}
