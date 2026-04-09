package com.cms.model;

import java.time.Instant;
import jakarta.persistence.*;

@Entity
@Table(name = "message_threads")
public class MessageThread {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, length = 300) private String subject;
    @Column(columnDefinition = "TEXT") private String participants;
    @Column(name = "last_message_at") private Instant lastMessageAt;
    @Column(nullable = false, length = 30) private String status = "ACTIVE";
    @Column(name = "created_at", nullable = false, updatable = false) private Instant createdAt;
    @PrePersist protected void onCreate() { createdAt = Instant.now(); lastMessageAt = Instant.now(); }

    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public String getSubject() { return subject; } public void setSubject(String v) { this.subject = v; }
    public String getParticipants() { return participants; } public void setParticipants(String v) { this.participants = v; }
    public Instant getLastMessageAt() { return lastMessageAt; } public void setLastMessageAt(Instant v) { this.lastMessageAt = v; }
    public String getStatus() { return status; } public void setStatus(String v) { this.status = v; }
    public Instant getCreatedAt() { return createdAt; }
}
