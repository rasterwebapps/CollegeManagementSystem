package com.cms.dto;

import java.time.Instant;
import java.time.LocalDate;

public record AdmissionResponse(
    Long id,
    Long studentId,
    String studentName,
    String applicationNumber,
    Long programId,
    String programName,
    LocalDate admissionDate,
    String admissionType,
    String status,
    String remarks,
    Instant createdAt,
    Instant updatedAt
) {}
