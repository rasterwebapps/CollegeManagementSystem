package com.cms.dto;

import com.cms.model.enums.LabStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LabRequest(
        @NotBlank String name,
        @NotNull Long labTypeId,
        @NotNull Long departmentId,
        String building,
        String floor,
        String roomNumber,
        @NotNull Integer capacity,
        String description,
        LabStatus status,
        Boolean active
) {
}
