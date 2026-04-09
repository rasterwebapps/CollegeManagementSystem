package com.cms.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record EquipmentResponse(
    Long id, Long labId, String labName,
    String name, String modelNumber, String serialNumber,
    LocalDate purchaseDate, BigDecimal purchaseCost, LocalDate warrantyExpiry,
    String status, Instant createdAt, Instant updatedAt
) {}
