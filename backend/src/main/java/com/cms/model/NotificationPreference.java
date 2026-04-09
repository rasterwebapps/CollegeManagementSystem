package com.cms.model;

import jakarta.persistence.*;

@Entity
@Table(name = "notification_preferences")
public class NotificationPreference {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(name = "keycloak_id", nullable = false, length = 255) private String keycloakId;
    @Column(nullable = false, length = 100) private String category;
    @Column(name = "email_enabled") private boolean emailEnabled = true;
    @Column(name = "sms_enabled") private boolean smsEnabled = false;
    @Column(name = "push_enabled") private boolean pushEnabled = true;

    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public String getKeycloakId() { return keycloakId; } public void setKeycloakId(String v) { this.keycloakId = v; }
    public String getCategory() { return category; } public void setCategory(String v) { this.category = v; }
    public boolean isEmailEnabled() { return emailEnabled; } public void setEmailEnabled(boolean v) { this.emailEnabled = v; }
    public boolean isSmsEnabled() { return smsEnabled; } public void setSmsEnabled(boolean v) { this.smsEnabled = v; }
    public boolean isPushEnabled() { return pushEnabled; } public void setPushEnabled(boolean v) { this.pushEnabled = v; }
}
