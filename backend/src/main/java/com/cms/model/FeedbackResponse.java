package com.cms.model;

import java.time.Instant;
import jakarta.persistence.*;

@Entity
@Table(name = "feedback_responses")
public class FeedbackResponse {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "form_id", nullable = false) private FeedbackForm form;
    @Column(name = "respondent_keycloak_id", length = 255) private String respondentKeycloakId;
    @Column(nullable = false, columnDefinition = "TEXT") private String answers;
    @Column(name = "submitted_at", nullable = false, updatable = false) private Instant submittedAt;
    @PrePersist protected void onCreate() { submittedAt = Instant.now(); }

    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public FeedbackForm getForm() { return form; } public void setForm(FeedbackForm v) { this.form = v; }
    public String getRespondentKeycloakId() { return respondentKeycloakId; } public void setRespondentKeycloakId(String v) { this.respondentKeycloakId = v; }
    public String getAnswers() { return answers; } public void setAnswers(String v) { this.answers = v; }
    public Instant getSubmittedAt() { return submittedAt; }
}
