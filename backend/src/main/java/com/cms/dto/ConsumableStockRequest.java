package com.cms.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ConsumableStockRequest(
    @NotNull(message = "Lab ID is required") Long labId,
    @NotBlank(message = "Item name is required") @Size(max = 200) String itemName,
    @NotNull(message = "Quantity is required") Integer quantity,
    @NotBlank(message = "Unit is required") @Size(max = 30) String unit,
    Integer minimumThreshold,
    BigDecimal unitCost,
    LocalDate lastRestocked,
    String status
) {}
