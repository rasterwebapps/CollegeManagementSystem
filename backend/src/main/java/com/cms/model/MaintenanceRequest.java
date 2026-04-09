package com.cms.model;

import java.time.Instant;
import jakarta.persistence.*;

@Entity
@Table(name = "maintenance_requests")
public class MaintenanceRequest {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(name = "requester_keycloak_id", nullable = false, length = 255) private String requesterKeycloakId;
    @Column(name = "requester_name", length = 200) private String requesterName;
    @Column(nullable = false, length = 200) private String location;
    @Column(nullable = false, columnDefinition = "TEXT") private String description;
    @Column(nullable = false, length = 20) private String priority = "MEDIUM";
    @Column(nullable = false, length = 30) private String status = "OPEN";
    @Column(name = "assigned_to", length = 200) private String assignedTo;
    @Column(columnDefinition = "TEXT") private String resolution;
    @Column(name = "created_at", nullable = false, updatable = false) private Instant createdAt;
    @Column(name = "updated_at", nullable = false) private Instant updatedAt;
    @PrePersist protected void onCreate() { createdAt = Instant.now(); updatedAt = Instant.now(); }
    @PreUpdate protected void onUpdate() { updatedAt = Instant.now(); }

    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public String getRequesterKeycloakId() { return requesterKeycloakId; } public void setRequesterKeycloakId(String v) { this.requesterKeycloakId = v; }
    public String getRequesterName() { return requesterName; } public void setRequesterName(String v) { this.requesterName = v; }
    public String getLocation() { return location; } public void setLocation(String v) { this.location = v; }
    public String getDescription() { return description; } public void setDescription(String v) { this.description = v; }
    public String getPriority() { return priority; } public void setPriority(String v) { this.priority = v; }
    public String getStatus() { return status; } public void setStatus(String v) { this.status = v; }
    public String getAssignedTo() { return assignedTo; } public void setAssignedTo(String v) { this.assignedTo = v; }
    public String getResolution() { return resolution; } public void setResolution(String v) { this.resolution = v; }
    public Instant getCreatedAt() { return createdAt; } public Instant getUpdatedAt() { return updatedAt; }
}
