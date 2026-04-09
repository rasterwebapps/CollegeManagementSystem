package com.cms.model;

import java.time.Instant;
import jakarta.persistence.*;

@Entity
@Table(name = "digital_resources")
public class DigitalResource {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, length = 300) private String title;
    @Column(nullable = false, length = 50) private String type;
    @Column(nullable = false, length = 500) private String url;
    @Column(name = "access_level", length = 50) private String accessLevel;
    @Column(length = 200) private String publisher;
    @Column(length = 200) private String author;
    @Column(columnDefinition = "TEXT") private String description;
    @Column(nullable = false, length = 30) private String status = "ACTIVE";
    @Column(name = "created_at", nullable = false, updatable = false) private Instant createdAt;
    @Column(name = "updated_at", nullable = false) private Instant updatedAt;
    @PrePersist protected void onCreate() { createdAt = Instant.now(); updatedAt = Instant.now(); }
    @PreUpdate protected void onUpdate() { updatedAt = Instant.now(); }

    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; } public void setTitle(String v) { this.title = v; }
    public String getType() { return type; } public void setType(String v) { this.type = v; }
    public String getUrl() { return url; } public void setUrl(String v) { this.url = v; }
    public String getAccessLevel() { return accessLevel; } public void setAccessLevel(String v) { this.accessLevel = v; }
    public String getPublisher() { return publisher; } public void setPublisher(String v) { this.publisher = v; }
    public String getAuthor() { return author; } public void setAuthor(String v) { this.author = v; }
    public String getDescription() { return description; } public void setDescription(String v) { this.description = v; }
    public String getStatus() { return status; } public void setStatus(String v) { this.status = v; }
    public Instant getCreatedAt() { return createdAt; } public Instant getUpdatedAt() { return updatedAt; }
}
