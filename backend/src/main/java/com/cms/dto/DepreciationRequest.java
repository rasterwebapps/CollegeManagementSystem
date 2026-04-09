package com.cms.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DepreciationRequest(
    @NotNull(message = "Asset ID is required") Long assetId,
    @NotBlank(message = "Fiscal year is required") @Size(max = 20) String fiscalYear,
    @NotNull(message = "Opening value is required") BigDecimal openingValue,
    @NotNull(message = "Depreciation amount is required") BigDecimal depreciationAmount,
    @NotNull(message = "Closing value is required") BigDecimal closingValue,
    @NotNull(message = "Calculated date is required") LocalDate calculatedDate
) {}
