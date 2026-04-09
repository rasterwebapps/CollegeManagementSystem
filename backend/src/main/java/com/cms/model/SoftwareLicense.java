package com.cms.model;

import java.time.LocalDate;
import java.time.Instant;
import jakarta.persistence.*;

@Entity
@Table(name = "software_licenses")
public class SoftwareLicense {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, length = 200) private String name;
    @Column(name = "license_count") private int licenseCount;
    @Column(length = 200) private String vendor;
    @Column(name = "license_key", length = 500) private String licenseKey;
    @Column(name = "expiry_date") private LocalDate expiryDate;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "lab_id") private Lab lab;
    @Column(nullable = false, length = 30) private String status = "ACTIVE";
    @Column(name = "created_at", nullable = false, updatable = false) private Instant createdAt;
    @Column(name = "updated_at", nullable = false) private Instant updatedAt;
    @PrePersist protected void onCreate() { createdAt = Instant.now(); updatedAt = Instant.now(); }
    @PreUpdate protected void onUpdate() { updatedAt = Instant.now(); }

    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public String getName() { return name; } public void setName(String v) { this.name = v; }
    public int getLicenseCount() { return licenseCount; } public void setLicenseCount(int v) { this.licenseCount = v; }
    public String getVendor() { return vendor; } public void setVendor(String v) { this.vendor = v; }
    public String getLicenseKey() { return licenseKey; } public void setLicenseKey(String v) { this.licenseKey = v; }
    public LocalDate getExpiryDate() { return expiryDate; } public void setExpiryDate(LocalDate v) { this.expiryDate = v; }
    public Lab getLab() { return lab; } public void setLab(Lab v) { this.lab = v; }
    public String getStatus() { return status; } public void setStatus(String v) { this.status = v; }
    public Instant getCreatedAt() { return createdAt; } public Instant getUpdatedAt() { return updatedAt; }
}
