package com.cms.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AdmissionRequest(
    @NotNull(message = "Student ID is required")
    Long studentId,

    @NotBlank(message = "Application number is required")
    @Size(max = 50, message = "Application number must not exceed 50 characters")
    String applicationNumber,

    @NotNull(message = "Program ID is required")
    Long programId,

    @NotNull(message = "Admission date is required")
    LocalDate admissionDate,

    @NotBlank(message = "Admission type is required")
    @Size(max = 30, message = "Admission type must not exceed 30 characters")
    String admissionType,

    String status,

    String remarks
) {}
