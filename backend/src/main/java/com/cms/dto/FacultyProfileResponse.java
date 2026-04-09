package com.cms.dto;

import java.time.Instant;
import java.time.LocalDate;

public record FacultyProfileResponse(
    Long id,
    String keycloakId,
    String employeeCode,
    String firstName,
    String lastName,
    String email,
    String phone,
    Long departmentId,
    String departmentName,
    String designation,
    LocalDate joiningDate,
    String status,
    Instant createdAt,
    Instant updatedAt
) {}
