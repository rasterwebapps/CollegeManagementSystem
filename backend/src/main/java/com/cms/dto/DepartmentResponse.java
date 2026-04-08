package com.cms.dto;

public record DepartmentResponse(
        Long id,
        String name,
        String code,
        String headName,
        String description,
        boolean active
) {
}
