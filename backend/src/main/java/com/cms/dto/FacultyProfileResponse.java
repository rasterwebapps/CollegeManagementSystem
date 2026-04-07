package com.cms.dto;

import java.time.LocalDate;

public record FacultyProfileResponse(
        Long id,
        String keycloakUserId,
        String employeeId,
        Long departmentId,
        String departmentName,
        String designation,
        String qualification,
        String specialization,
        LocalDate joiningDate,
        String phone,
        boolean active
) {
}
