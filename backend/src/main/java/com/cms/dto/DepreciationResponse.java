package com.cms.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record DepreciationResponse(
    Long id, Long assetId, String assetName,
    String fiscalYear, BigDecimal openingValue,
    BigDecimal depreciationAmount, BigDecimal closingValue,
    LocalDate calculatedDate, Instant createdAt, Instant updatedAt
) {}
