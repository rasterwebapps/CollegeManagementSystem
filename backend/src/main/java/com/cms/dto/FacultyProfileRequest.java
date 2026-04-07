package com.cms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record FacultyProfileRequest(
        @NotBlank String keycloakUserId,
        @NotBlank String employeeId,
        @NotNull Long departmentId,
        String designation,
        String qualification,
        String specialization,
        LocalDate joiningDate,
        String phone,
        Boolean active
) {
}
