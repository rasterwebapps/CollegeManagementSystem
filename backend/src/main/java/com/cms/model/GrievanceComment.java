package com.cms.model;

import java.time.Instant;
import jakarta.persistence.*;

@Entity
@Table(name = "grievance_comments")
public class GrievanceComment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "grievance_id", nullable = false) private Grievance grievance;
    @Column(name = "commenter_keycloak_id", nullable = false, length = 255) private String commenterKeycloakId;
    @Column(name = "commenter_name", length = 200) private String commenterName;
    @Column(nullable = false, columnDefinition = "TEXT") private String comment;
    @Column(name = "is_internal") private boolean internal = false;
    @Column(name = "created_at", nullable = false, updatable = false) private Instant createdAt;
    @PrePersist protected void onCreate() { createdAt = Instant.now(); }

    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public Grievance getGrievance() { return grievance; } public void setGrievance(Grievance v) { this.grievance = v; }
    public String getCommenterKeycloakId() { return commenterKeycloakId; } public void setCommenterKeycloakId(String v) { this.commenterKeycloakId = v; }
    public String getCommenterName() { return commenterName; } public void setCommenterName(String v) { this.commenterName = v; }
    public String getComment() { return comment; } public void setComment(String v) { this.comment = v; }
    public boolean isInternal() { return internal; } public void setInternal(boolean v) { this.internal = v; }
    public Instant getCreatedAt() { return createdAt; }
}
