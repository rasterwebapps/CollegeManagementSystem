package com.cms.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record PracticalExamResponse(
    Long id, Long courseId, String courseName,
    Long semesterId, String semesterName,
    Long experimentId, String experimentTitle,
    LocalDate examDate, String startTime, String endTime,
    Long labId, String labName,
    BigDecimal maxMarks, BigDecimal passingMarks,
    String examiner, String status,
    Instant createdAt, Instant updatedAt
) {}
