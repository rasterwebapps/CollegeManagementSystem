package com.cms.dto;

import com.cms.model.enums.CalendarEventType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CalendarEventRequest(
        @NotNull Long academicYearId,
        @NotBlank String title,
        @NotNull CalendarEventType eventType,
        @NotNull LocalDate startDate,
        @NotNull LocalDate endDate,
        String description
) {
}
