package com.cms.dto;

import jakarta.validation.constraints.NotBlank;

public record DepartmentRequest(
        @NotBlank String name,
        @NotBlank String code,
        String headName,
        String description,
        Boolean active
) {
}
