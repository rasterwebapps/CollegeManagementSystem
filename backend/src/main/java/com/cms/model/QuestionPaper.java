package com.cms.model;

import java.time.Instant;
import jakarta.persistence.*;

@Entity
@Table(name = "question_papers")
public class QuestionPaper {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "exam_id", nullable = false) private ExamSchedule exam;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "course_id", nullable = false) private Course course;
    @Column(name = "total_marks", nullable = false) private int totalMarks;
    @Column(columnDefinition = "TEXT") private String sections;
    @Column(name = "approved_by", length = 200) private String approvedBy;
    @Column(nullable = false, length = 30) private String status = "DRAFT";
    @Column(name = "created_at", nullable = false, updatable = false) private Instant createdAt;
    @Column(name = "updated_at", nullable = false) private Instant updatedAt;
    @PrePersist protected void onCreate() { createdAt = Instant.now(); updatedAt = Instant.now(); }
    @PreUpdate protected void onUpdate() { updatedAt = Instant.now(); }

    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public ExamSchedule getExam() { return exam; } public void setExam(ExamSchedule v) { this.exam = v; }
    public Course getCourse() { return course; } public void setCourse(Course v) { this.course = v; }
    public int getTotalMarks() { return totalMarks; } public void setTotalMarks(int v) { this.totalMarks = v; }
    public String getSections() { return sections; } public void setSections(String v) { this.sections = v; }
    public String getApprovedBy() { return approvedBy; } public void setApprovedBy(String v) { this.approvedBy = v; }
    public String getStatus() { return status; } public void setStatus(String v) { this.status = v; }
    public Instant getCreatedAt() { return createdAt; } public Instant getUpdatedAt() { return updatedAt; }
}
