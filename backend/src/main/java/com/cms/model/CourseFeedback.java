package com.cms.model;

import java.math.BigDecimal;
import java.time.Instant;
import jakarta.persistence.*;

@Entity
@Table(name = "course_feedback")
public class CourseFeedback {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "course_id", nullable = false) private Course course;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "semester_id", nullable = false) private Semester semester;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "student_id") private StudentProfile student;
    @Column(precision = 4, scale = 2) private BigDecimal rating;
    @Column(columnDefinition = "TEXT") private String comments;
    @Column(name = "submitted_at", nullable = false, updatable = false) private Instant submittedAt;
    @PrePersist protected void onCreate() { submittedAt = Instant.now(); }

    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public Course getCourse() { return course; } public void setCourse(Course v) { this.course = v; }
    public Semester getSemester() { return semester; } public void setSemester(Semester v) { this.semester = v; }
    public StudentProfile getStudent() { return student; } public void setStudent(StudentProfile v) { this.student = v; }
    public BigDecimal getRating() { return rating; } public void setRating(BigDecimal v) { this.rating = v; }
    public String getComments() { return comments; } public void setComments(String v) { this.comments = v; }
    public Instant getSubmittedAt() { return submittedAt; }
}
