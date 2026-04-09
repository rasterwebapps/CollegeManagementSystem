package com.cms.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record ContinuousEvaluationResponse(
    Long id, Long studentId, String studentName,
    Long courseId, String courseName,
    Long experimentId, String experimentTitle,
    Long semesterId, String semesterName,
    String evaluationType, BigDecimal marksObtained, BigDecimal maxMarks,
    LocalDate evaluationDate, String remarks,
    Instant createdAt, Instant updatedAt
) {}
