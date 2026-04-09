package com.cms.model;

import java.time.Instant;
import jakarta.persistence.*;

@Entity
@Table(name = "notifications")
public class Notification {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(name = "recipient_keycloak_id", nullable = false, length = 255) private String recipientKeycloakId;
    @Column(nullable = false, length = 300) private String title;
    @Column(columnDefinition = "TEXT") private String message;
    @Column(nullable = false, length = 50) private String type;
    @Column(name = "read_status", nullable = false) private boolean readStatus = false;
    @Column(length = 500) private String link;
    @Column(name = "created_at", nullable = false, updatable = false) private Instant createdAt;
    @PrePersist protected void onCreate() { createdAt = Instant.now(); }

    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public String getRecipientKeycloakId() { return recipientKeycloakId; } public void setRecipientKeycloakId(String v) { this.recipientKeycloakId = v; }
    public String getTitle() { return title; } public void setTitle(String v) { this.title = v; }
    public String getMessage() { return message; } public void setMessage(String v) { this.message = v; }
    public String getType() { return type; } public void setType(String v) { this.type = v; }
    public boolean isReadStatus() { return readStatus; } public void setReadStatus(boolean v) { this.readStatus = v; }
    public String getLink() { return link; } public void setLink(String v) { this.link = v; }
    public Instant getCreatedAt() { return createdAt; }
}
