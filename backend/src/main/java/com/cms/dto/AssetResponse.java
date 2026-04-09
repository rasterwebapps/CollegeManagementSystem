package com.cms.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record AssetResponse(
    Long id, Long equipmentId, String equipmentName,
    String assetCode, String name, String category,
    LocalDate purchaseDate, BigDecimal purchaseCost, BigDecimal currentValue,
    Integer usefulLifeYears, String depreciationMethod, String location, String status,
    Instant createdAt, Instant updatedAt
) {}
