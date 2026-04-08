package com.cms.dto;

public record LabSummaryResponse(
        long totalLabs,
        long activeLabs,
        long underMaintenance,
        long inactive
) {
}
