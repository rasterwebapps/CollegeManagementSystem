package com.cms.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Instant;
import jakarta.persistence.*;

@Entity
@Table(name = "placement_offers")
public class PlacementOffer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "student_id", nullable = false) private StudentProfile student;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "company_id", nullable = false) private Company company;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "drive_id") private PlacementDrive drive;
    @Column(nullable = false, length = 200) private String role;
    @Column(name = "package_amount", precision = 14, scale = 2) private BigDecimal packageAmount;
    @Column(name = "joining_date") private LocalDate joiningDate;
    @Column(nullable = false, length = 30) private String status = "OFFERED";
    @Column(name = "created_at", nullable = false, updatable = false) private Instant createdAt;
    @Column(name = "updated_at", nullable = false) private Instant updatedAt;
    @PrePersist protected void onCreate() { createdAt = Instant.now(); updatedAt = Instant.now(); }
    @PreUpdate protected void onUpdate() { updatedAt = Instant.now(); }

    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public StudentProfile getStudent() { return student; } public void setStudent(StudentProfile v) { this.student = v; }
    public Company getCompany() { return company; } public void setCompany(Company v) { this.company = v; }
    public PlacementDrive getDrive() { return drive; } public void setDrive(PlacementDrive v) { this.drive = v; }
    public String getRole() { return role; } public void setRole(String v) { this.role = v; }
    public BigDecimal getPackageAmount() { return packageAmount; } public void setPackageAmount(BigDecimal v) { this.packageAmount = v; }
    public LocalDate getJoiningDate() { return joiningDate; } public void setJoiningDate(LocalDate v) { this.joiningDate = v; }
    public String getStatus() { return status; } public void setStatus(String v) { this.status = v; }
    public Instant getCreatedAt() { return createdAt; } public Instant getUpdatedAt() { return updatedAt; }
}
