package com.cms.model;

import java.time.Instant;
import jakarta.persistence.*;

@Entity
@Table(name = "messages")
public class Message {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(name = "sender_keycloak_id", nullable = false, length = 255) private String senderKeycloakId;
    @Column(name = "receiver_keycloak_id", nullable = false, length = 255) private String receiverKeycloakId;
    @Column(length = 300) private String subject;
    @Column(nullable = false, columnDefinition = "TEXT") private String body;
    @Column(name = "read_status", nullable = false) private boolean readStatus = false;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "thread_id") private MessageThread thread;
    @Column(name = "created_at", nullable = false, updatable = false) private Instant createdAt;
    @PrePersist protected void onCreate() { createdAt = Instant.now(); }

    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public String getSenderKeycloakId() { return senderKeycloakId; } public void setSenderKeycloakId(String v) { this.senderKeycloakId = v; }
    public String getReceiverKeycloakId() { return receiverKeycloakId; } public void setReceiverKeycloakId(String v) { this.receiverKeycloakId = v; }
    public String getSubject() { return subject; } public void setSubject(String v) { this.subject = v; }
    public String getBody() { return body; } public void setBody(String v) { this.body = v; }
    public boolean isReadStatus() { return readStatus; } public void setReadStatus(boolean v) { this.readStatus = v; }
    public MessageThread getThread() { return thread; } public void setThread(MessageThread v) { this.thread = v; }
    public Instant getCreatedAt() { return createdAt; }
}
