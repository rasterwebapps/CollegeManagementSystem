package com.cms.model;

import java.time.Instant;
import jakarta.persistence.*;

@Entity
@Table(name = "vehicles")
public class Vehicle {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(name = "registration_number", nullable = false, unique = true, length = 50) private String registrationNumber;
    @Column(nullable = false, length = 50) private String type;
    @Column(nullable = false) private int capacity;
    @Column(name = "driver_name", length = 200) private String driverName;
    @Column(name = "driver_phone", length = 20) private String driverPhone;
    @Column(name = "insurance_expiry") private java.time.LocalDate insuranceExpiry;
    @Column(nullable = false, length = 30) private String status = "ACTIVE";
    @Column(name = "created_at", nullable = false, updatable = false) private Instant createdAt;
    @Column(name = "updated_at", nullable = false) private Instant updatedAt;
    @PrePersist protected void onCreate() { createdAt = Instant.now(); updatedAt = Instant.now(); }
    @PreUpdate protected void onUpdate() { updatedAt = Instant.now(); }

    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public String getRegistrationNumber() { return registrationNumber; } public void setRegistrationNumber(String v) { this.registrationNumber = v; }
    public String getType() { return type; } public void setType(String v) { this.type = v; }
    public int getCapacity() { return capacity; } public void setCapacity(int v) { this.capacity = v; }
    public String getDriverName() { return driverName; } public void setDriverName(String v) { this.driverName = v; }
    public String getDriverPhone() { return driverPhone; } public void setDriverPhone(String v) { this.driverPhone = v; }
    public java.time.LocalDate getInsuranceExpiry() { return insuranceExpiry; } public void setInsuranceExpiry(java.time.LocalDate v) { this.insuranceExpiry = v; }
    public String getStatus() { return status; } public void setStatus(String v) { this.status = v; }
    public Instant getCreatedAt() { return createdAt; } public Instant getUpdatedAt() { return updatedAt; }
}
