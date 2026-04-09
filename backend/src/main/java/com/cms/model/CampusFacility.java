package com.cms.model;

import java.time.Instant;
import jakarta.persistence.*;

@Entity
@Table(name = "campus_facilities")
public class CampusFacility {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, length = 200) private String name;
    @Column(nullable = false, length = 100) private String type;
    @Column private int capacity;
    @Column(length = 200) private String location;
    @Column(columnDefinition = "TEXT") private String description;
    @Column(nullable = false, length = 30) private String status = "AVAILABLE";
    @Column(name = "created_at", nullable = false, updatable = false) private Instant createdAt;
    @Column(name = "updated_at", nullable = false) private Instant updatedAt;
    @PrePersist protected void onCreate() { createdAt = Instant.now(); updatedAt = Instant.now(); }
    @PreUpdate protected void onUpdate() { updatedAt = Instant.now(); }

    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public String getName() { return name; } public void setName(String v) { this.name = v; }
    public String getType() { return type; } public void setType(String v) { this.type = v; }
    public int getCapacity() { return capacity; } public void setCapacity(int v) { this.capacity = v; }
    public String getLocation() { return location; } public void setLocation(String v) { this.location = v; }
    public String getDescription() { return description; } public void setDescription(String v) { this.description = v; }
    public String getStatus() { return status; } public void setStatus(String v) { this.status = v; }
    public Instant getCreatedAt() { return createdAt; } public Instant getUpdatedAt() { return updatedAt; }
}
