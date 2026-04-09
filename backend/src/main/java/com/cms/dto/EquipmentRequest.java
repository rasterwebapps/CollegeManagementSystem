package com.cms.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EquipmentRequest(
    @NotNull(message = "Lab ID is required") Long labId,
    @NotBlank(message = "Name is required") @Size(max = 200) String name,
    @Size(max = 100) String modelNumber,
    @Size(max = 100) String serialNumber,
    LocalDate purchaseDate,
    BigDecimal purchaseCost,
    LocalDate warrantyExpiry,
    String status
) {}
