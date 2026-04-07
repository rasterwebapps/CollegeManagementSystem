package com.cms.dto;

import jakarta.validation.constraints.NotBlank;

public record LabTypeRequest(
        @NotBlank String name,
        String description
) {
}
