package com.cms.dto;

import com.cms.model.enums.LabStatus;

public record LabResponse(
        Long id,
        String name,
        Long labTypeId,
        String labTypeName,
        Long departmentId,
        String departmentName,
        String building,
        String floor,
        String roomNumber,
        int capacity,
        String description,
        LabStatus status,
        boolean active
) {
}
