package com.cms.model;

import java.time.LocalDate;
import java.time.Instant;
import jakarta.persistence.*;

@Entity
@Table(name = "lab_safety_records")
public class LabSafetyRecord {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "lab_id", nullable = false) private Lab lab;
    @Column(name = "certificate_type", nullable = false, length = 100) private String certificateType;
    @Column(name = "issue_date", nullable = false) private LocalDate issueDate;
    @Column(name = "expiry_date") private LocalDate expiryDate;
    @Column(name = "inspector_name", length = 200) private String inspectorName;
    @Column(columnDefinition = "TEXT") private String remarks;
    @Column(nullable = false, length = 30) private String status = "VALID";
    @Column(name = "created_at", nullable = false, updatable = false) private Instant createdAt;
    @PrePersist protected void onCreate() { createdAt = Instant.now(); }

    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public Lab getLab() { return lab; } public void setLab(Lab v) { this.lab = v; }
    public String getCertificateType() { return certificateType; } public void setCertificateType(String v) { this.certificateType = v; }
    public LocalDate getIssueDate() { return issueDate; } public void setIssueDate(LocalDate v) { this.issueDate = v; }
    public LocalDate getExpiryDate() { return expiryDate; } public void setExpiryDate(LocalDate v) { this.expiryDate = v; }
    public String getInspectorName() { return inspectorName; } public void setInspectorName(String v) { this.inspectorName = v; }
    public String getRemarks() { return remarks; } public void setRemarks(String v) { this.remarks = v; }
    public String getStatus() { return status; } public void setStatus(String v) { this.status = v; }
    public Instant getCreatedAt() { return createdAt; }
}
