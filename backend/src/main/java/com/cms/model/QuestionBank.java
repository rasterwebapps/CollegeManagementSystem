package com.cms.model;

import java.time.Instant;
import jakarta.persistence.*;

@Entity
@Table(name = "question_bank")
public class QuestionBank {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "course_id", nullable = false) private Course course;
    @Column(nullable = false) private int unit;
    @Column(nullable = false, columnDefinition = "TEXT") private String question;
    @Column(nullable = false, length = 50) private String type;
    @Column(name = "bloom_level", nullable = false, length = 30) private String bloomLevel;
    @Column(nullable = false) private int marks;
    @Column(name = "created_by", length = 200) private String createdBy;
    @Column(name = "created_at", nullable = false, updatable = false) private Instant createdAt;
    @PrePersist protected void onCreate() { createdAt = Instant.now(); }

    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public Course getCourse() { return course; } public void setCourse(Course v) { this.course = v; }
    public int getUnit() { return unit; } public void setUnit(int v) { this.unit = v; }
    public String getQuestion() { return question; } public void setQuestion(String v) { this.question = v; }
    public String getType() { return type; } public void setType(String v) { this.type = v; }
    public String getBloomLevel() { return bloomLevel; } public void setBloomLevel(String v) { this.bloomLevel = v; }
    public int getMarks() { return marks; } public void setMarks(int v) { this.marks = v; }
    public String getCreatedBy() { return createdBy; } public void setCreatedBy(String v) { this.createdBy = v; }
    public Instant getCreatedAt() { return createdAt; }
}
