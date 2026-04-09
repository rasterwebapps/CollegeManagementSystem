package com.cms.dto;

import java.time.Instant;
import java.time.LocalDate;

public record StudentProfileResponse(
    Long id,
    String keycloakId,
    String enrollmentNumber,
    String firstName,
    String lastName,
    String email,
    String phone,
    Long programId,
    String programName,
    Long departmentId,
    String departmentName,
    Integer currentSemester,
    LocalDate admissionDate,
    String status,
    Instant createdAt,
    Instant updatedAt
) {}
