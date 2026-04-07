package com.cms.dto;

import com.cms.model.enums.CalendarEventType;

import java.time.LocalDate;

public record CalendarEventResponse(
        Long id,
        Long academicYearId,
        String academicYearName,
        String title,
        CalendarEventType eventType,
        LocalDate startDate,
        LocalDate endDate,
        String description
) {
}
