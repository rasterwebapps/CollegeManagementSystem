package com.cms.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record StudentProfileRequest(
    @NotBlank(message = "Keycloak ID is required")
    @Size(max = 255, message = "Keycloak ID must not exceed 255 characters")
    String keycloakId,

    @NotBlank(message = "Enrollment number is required")
    @Size(max = 50, message = "Enrollment number must not exceed 50 characters")
    String enrollmentNumber,

    @NotBlank(message = "First name is required")
    @Size(max = 100, message = "First name must not exceed 100 characters")
    String firstName,

    @NotBlank(message = "Last name is required")
    @Size(max = 100, message = "Last name must not exceed 100 characters")
    String lastName,

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Size(max = 255, message = "Email must not exceed 255 characters")
    String email,

    @Size(max = 20, message = "Phone must not exceed 20 characters")
    String phone,

    @NotNull(message = "Program ID is required")
    Long programId,

    @NotNull(message = "Department ID is required")
    Long departmentId,

    @NotNull(message = "Current semester is required")
    Integer currentSemester,

    @NotNull(message = "Admission date is required")
    LocalDate admissionDate,

    String status
) {}
