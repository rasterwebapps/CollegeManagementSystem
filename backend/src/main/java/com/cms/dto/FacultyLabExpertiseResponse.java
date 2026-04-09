package com.cms.dto;

import java.time.Instant;
import java.time.LocalDate;

public record FacultyLabExpertiseResponse(
    Long id,
    Long facultyId,
    String facultyName,
    Long labTypeId,
    String labTypeName,
    String proficiencyLevel,
    LocalDate certifiedDate,
    Instant createdAt,
    Instant updatedAt
) {}
