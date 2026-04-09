package com.cms.dto;

import java.time.Instant;

public record LabResponse(
    Long id,
    String name,
    String code,
    Long labTypeId,
    String labTypeName,
    Long departmentId,
    String departmentName,
    String location,
    Integer capacity,
    String status,
    Instant createdAt,
    Instant updatedAt
) {}
