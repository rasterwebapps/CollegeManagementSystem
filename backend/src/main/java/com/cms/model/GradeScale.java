package com.cms.model;

import java.math.BigDecimal;
import jakarta.persistence.*;

@Entity
@Table(name = "grade_scales")
public class GradeScale {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, length = 5) private String grade;
    @Column(name = "min_percentage", nullable = false, precision = 5, scale = 2) private BigDecimal minPercentage;
    @Column(name = "max_percentage", nullable = false, precision = 5, scale = 2) private BigDecimal maxPercentage;
    @Column(name = "grade_point", nullable = false, precision = 4, scale = 2) private BigDecimal gradePoint;

    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public String getGrade() { return grade; } public void setGrade(String v) { this.grade = v; }
    public BigDecimal getMinPercentage() { return minPercentage; } public void setMinPercentage(BigDecimal v) { this.minPercentage = v; }
    public BigDecimal getMaxPercentage() { return maxPercentage; } public void setMaxPercentage(BigDecimal v) { this.maxPercentage = v; }
    public BigDecimal getGradePoint() { return gradePoint; } public void setGradePoint(BigDecimal v) { this.gradePoint = v; }
}
