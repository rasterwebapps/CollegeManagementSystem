package com.cms.model;

import java.math.BigDecimal;
import java.time.Instant;
import jakarta.persistence.*;

@Entity
@Table(name = "faculty_appraisals")
public class FacultyAppraisal {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "faculty_id", nullable = false) private FacultyProfile faculty;
    @Column(name = "appraisal_year", nullable = false) private int year;
    @Column(name = "teaching_rating", precision = 4, scale = 2) private BigDecimal teachingRating;
    @Column(name = "research_rating", precision = 4, scale = 2) private BigDecimal researchRating;
    @Column(name = "service_rating", precision = 4, scale = 2) private BigDecimal serviceRating;
    @Column(name = "overall_rating", precision = 4, scale = 2) private BigDecimal overallRating;
    @Column(name = "reviewed_by", length = 200) private String reviewedBy;
    @Column(columnDefinition = "TEXT") private String remarks;
    @Column(nullable = false, length = 30) private String status = "DRAFT";
    @Column(name = "created_at", nullable = false, updatable = false) private Instant createdAt;
    @Column(name = "updated_at", nullable = false) private Instant updatedAt;
    @PrePersist protected void onCreate() { createdAt = Instant.now(); updatedAt = Instant.now(); }
    @PreUpdate protected void onUpdate() { updatedAt = Instant.now(); }

    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public FacultyProfile getFaculty() { return faculty; } public void setFaculty(FacultyProfile v) { this.faculty = v; }
    public int getYear() { return year; } public void setYear(int v) { this.year = v; }
    public BigDecimal getTeachingRating() { return teachingRating; } public void setTeachingRating(BigDecimal v) { this.teachingRating = v; }
    public BigDecimal getResearchRating() { return researchRating; } public void setResearchRating(BigDecimal v) { this.researchRating = v; }
    public BigDecimal getServiceRating() { return serviceRating; } public void setServiceRating(BigDecimal v) { this.serviceRating = v; }
    public BigDecimal getOverallRating() { return overallRating; } public void setOverallRating(BigDecimal v) { this.overallRating = v; }
    public String getReviewedBy() { return reviewedBy; } public void setReviewedBy(String v) { this.reviewedBy = v; }
    public String getRemarks() { return remarks; } public void setRemarks(String v) { this.remarks = v; }
    public String getStatus() { return status; } public void setStatus(String v) { this.status = v; }
    public Instant getCreatedAt() { return createdAt; } public Instant getUpdatedAt() { return updatedAt; }
}
