package com.cms.model;

import java.math.BigDecimal;
import java.time.Instant;
import jakarta.persistence.*;

@Entity
@Table(name = "revaluation_requests")
public class RevaluationRequest {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "student_id", nullable = false) private StudentProfile student;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "exam_id", nullable = false) private ExamSchedule exam;
    @Column(columnDefinition = "TEXT") private String reason;
    @Column(nullable = false, length = 30) private String status = "PENDING";
    @Column(name = "revised_marks", precision = 6, scale = 2) private BigDecimal revisedMarks;
    @Column(name = "created_at", nullable = false, updatable = false) private Instant createdAt;
    @Column(name = "updated_at", nullable = false) private Instant updatedAt;
    @PrePersist protected void onCreate() { createdAt = Instant.now(); updatedAt = Instant.now(); }
    @PreUpdate protected void onUpdate() { updatedAt = Instant.now(); }

    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public StudentProfile getStudent() { return student; } public void setStudent(StudentProfile v) { this.student = v; }
    public ExamSchedule getExam() { return exam; } public void setExam(ExamSchedule v) { this.exam = v; }
    public String getReason() { return reason; } public void setReason(String v) { this.reason = v; }
    public String getStatus() { return status; } public void setStatus(String v) { this.status = v; }
    public BigDecimal getRevisedMarks() { return revisedMarks; } public void setRevisedMarks(BigDecimal v) { this.revisedMarks = v; }
    public Instant getCreatedAt() { return createdAt; } public Instant getUpdatedAt() { return updatedAt; }
}
