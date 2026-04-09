package com.cms.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AssetRequest(
    Long equipmentId,
    @NotBlank(message = "Asset code is required") @Size(max = 50) String assetCode,
    @NotBlank(message = "Name is required") @Size(max = 200) String name,
    @NotBlank(message = "Category is required") @Size(max = 100) String category,
    LocalDate purchaseDate,
    BigDecimal purchaseCost,
    BigDecimal currentValue,
    Integer usefulLifeYears,
    @Size(max = 50) String depreciationMethod,
    @Size(max = 200) String location,
    String status
) {}
