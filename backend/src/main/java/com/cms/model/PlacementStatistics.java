package com.cms.model;

import java.math.BigDecimal;
import jakarta.persistence.*;

@Entity
@Table(name = "placement_statistics")
public class PlacementStatistics {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "academic_year_id", nullable = false) private AcademicYear academicYear;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "department_id", nullable = false) private Department department;
    @Column(name = "total_eligible") private int totalEligible;
    @Column(name = "total_placed") private int totalPlaced;
    @Column(name = "highest_package", precision = 14, scale = 2) private BigDecimal highestPackage;
    @Column(name = "average_package", precision = 14, scale = 2) private BigDecimal averagePackage;
    @Column(name = "median_package", precision = 14, scale = 2) private BigDecimal medianPackage;

    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public AcademicYear getAcademicYear() { return academicYear; } public void setAcademicYear(AcademicYear v) { this.academicYear = v; }
    public Department getDepartment() { return department; } public void setDepartment(Department v) { this.department = v; }
    public int getTotalEligible() { return totalEligible; } public void setTotalEligible(int v) { this.totalEligible = v; }
    public int getTotalPlaced() { return totalPlaced; } public void setTotalPlaced(int v) { this.totalPlaced = v; }
    public BigDecimal getHighestPackage() { return highestPackage; } public void setHighestPackage(BigDecimal v) { this.highestPackage = v; }
    public BigDecimal getAveragePackage() { return averagePackage; } public void setAveragePackage(BigDecimal v) { this.averagePackage = v; }
    public BigDecimal getMedianPackage() { return medianPackage; } public void setMedianPackage(BigDecimal v) { this.medianPackage = v; }
}
