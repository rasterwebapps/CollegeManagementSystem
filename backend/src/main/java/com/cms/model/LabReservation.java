package com.cms.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Instant;
import jakarta.persistence.*;

@Entity
@Table(name = "lab_reservations")
public class LabReservation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "lab_id", nullable = false) private Lab lab;
    @Column(name = "reserved_by_keycloak_id", nullable = false, length = 255) private String reservedByKeycloakId;
    @Column(name = "reserved_by_name", length = 200) private String reservedByName;
    @Column(name = "reservation_date", nullable = false) private LocalDate reservationDate;
    @Column(name = "start_time", nullable = false) private LocalTime startTime;
    @Column(name = "end_time", nullable = false) private LocalTime endTime;
    @Column(columnDefinition = "TEXT") private String purpose;
    @Column(nullable = false, length = 30) private String status = "PENDING";
    @Column(name = "created_at", nullable = false, updatable = false) private Instant createdAt;
    @Column(name = "updated_at", nullable = false) private Instant updatedAt;
    @PrePersist protected void onCreate() { createdAt = Instant.now(); updatedAt = Instant.now(); }
    @PreUpdate protected void onUpdate() { updatedAt = Instant.now(); }

    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public Lab getLab() { return lab; } public void setLab(Lab v) { this.lab = v; }
    public String getReservedByKeycloakId() { return reservedByKeycloakId; } public void setReservedByKeycloakId(String v) { this.reservedByKeycloakId = v; }
    public String getReservedByName() { return reservedByName; } public void setReservedByName(String v) { this.reservedByName = v; }
    public LocalDate getReservationDate() { return reservationDate; } public void setReservationDate(LocalDate v) { this.reservationDate = v; }
    public LocalTime getStartTime() { return startTime; } public void setStartTime(LocalTime v) { this.startTime = v; }
    public LocalTime getEndTime() { return endTime; } public void setEndTime(LocalTime v) { this.endTime = v; }
    public String getPurpose() { return purpose; } public void setPurpose(String v) { this.purpose = v; }
    public String getStatus() { return status; } public void setStatus(String v) { this.status = v; }
    public Instant getCreatedAt() { return createdAt; } public Instant getUpdatedAt() { return updatedAt; }
}
