package com.cms.dto;

import java.time.Instant;
import java.time.LocalDate;

public record CalendarEventResponse(
    Long id,
    String title,
    String description,
    LocalDate eventDate,
    String eventType,
    Long academicYearId,
    String academicYearName,
    Instant createdAt,
    Instant updatedAt
) {}
