package com.cms.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record ConsumableStockResponse(
    Long id, Long labId, String labName,
    String itemName, Integer quantity, String unit,
    Integer minimumThreshold, BigDecimal unitCost, LocalDate lastRestocked,
    String status, Instant createdAt, Instant updatedAt
) {}
