package com.cms.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "gpa_records")
public class GpaRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private StudentProfile student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "semester_id", nullable = false)
    private Semester semester;

    @Column(name = "semester_gpa", nullable = false, precision = 4, scale = 2)
    private BigDecimal semesterGpa;

    @Column(nullable = false, precision = 4, scale = 2)
    private BigDecimal cgpa;

    @Column(name = "total_credits", nullable = false)
    private Integer totalCredits;

    @Column(name = "earned_credits", nullable = false)
    private Integer earnedCredits;

    @Column(name = "lab_component_gpa", precision = 4, scale = 2)
    private BigDecimal labComponentGpa;

    @Column(name = "calculation_date", nullable = false)
    private LocalDate calculationDate;

    @Column(length = 30, nullable = false)
    private String status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @PrePersist
    protected void onCreate() { createdAt = updatedAt = Instant.now(); }

    @PreUpdate
    protected void onUpdate() { updatedAt = Instant.now(); }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public StudentProfile getStudent() { return student; }
    public void setStudent(StudentProfile student) { this.student = student; }
    public Semester getSemester() { return semester; }
    public void setSemester(Semester semester) { this.semester = semester; }
    public BigDecimal getSemesterGpa() { return semesterGpa; }
    public void setSemesterGpa(BigDecimal semesterGpa) { this.semesterGpa = semesterGpa; }
    public BigDecimal getCgpa() { return cgpa; }
    public void setCgpa(BigDecimal cgpa) { this.cgpa = cgpa; }
    public Integer getTotalCredits() { return totalCredits; }
    public void setTotalCredits(Integer totalCredits) { this.totalCredits = totalCredits; }
    public Integer getEarnedCredits() { return earnedCredits; }
    public void setEarnedCredits(Integer earnedCredits) { this.earnedCredits = earnedCredits; }
    public BigDecimal getLabComponentGpa() { return labComponentGpa; }
    public void setLabComponentGpa(BigDecimal labComponentGpa) { this.labComponentGpa = labComponentGpa; }
    public LocalDate getCalculationDate() { return calculationDate; }
    public void setCalculationDate(LocalDate calculationDate) { this.calculationDate = calculationDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
