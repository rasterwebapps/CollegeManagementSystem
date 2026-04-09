package com.cms.model;

import java.time.LocalDate;
import java.time.Instant;
import jakarta.persistence.*;

@Entity
@Table(name = "feedback_forms")
public class FeedbackForm {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, length = 300) private String title;
    @Column(name = "target_audience", nullable = false, length = 100) private String targetAudience;
    @Column(nullable = false, columnDefinition = "TEXT") private String questions;
    @Column(name = "start_date", nullable = false) private LocalDate startDate;
    @Column(name = "end_date", nullable = false) private LocalDate endDate;
    @Column(name = "is_anonymous") private boolean anonymous = false;
    @Column(nullable = false, length = 30) private String status = "DRAFT";
    @Column(name = "created_at", nullable = false, updatable = false) private Instant createdAt;
    @Column(name = "updated_at", nullable = false) private Instant updatedAt;
    @PrePersist protected void onCreate() { createdAt = Instant.now(); updatedAt = Instant.now(); }
    @PreUpdate protected void onUpdate() { updatedAt = Instant.now(); }

    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; } public void setTitle(String v) { this.title = v; }
    public String getTargetAudience() { return targetAudience; } public void setTargetAudience(String v) { this.targetAudience = v; }
    public String getQuestions() { return questions; } public void setQuestions(String v) { this.questions = v; }
    public LocalDate getStartDate() { return startDate; } public void setStartDate(LocalDate v) { this.startDate = v; }
    public LocalDate getEndDate() { return endDate; } public void setEndDate(LocalDate v) { this.endDate = v; }
    public boolean isAnonymous() { return anonymous; } public void setAnonymous(boolean v) { this.anonymous = v; }
    public String getStatus() { return status; } public void setStatus(String v) { this.status = v; }
    public Instant getCreatedAt() { return createdAt; } public Instant getUpdatedAt() { return updatedAt; }
}
