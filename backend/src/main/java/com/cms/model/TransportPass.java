package com.cms.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Instant;
import jakarta.persistence.*;

@Entity
@Table(name = "transport_passes")
public class TransportPass {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "student_id") private StudentProfile student;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "faculty_id") private FacultyProfile faculty;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "route_id", nullable = false) private TransportRoute route;
    @Column(name = "valid_from", nullable = false) private LocalDate validFrom;
    @Column(name = "valid_to", nullable = false) private LocalDate validTo;
    @Column(name = "pass_type", nullable = false, length = 50) private String passType;
    @Column(precision = 10, scale = 2) private BigDecimal fee;
    @Column(nullable = false, length = 30) private String status = "ACTIVE";
    @Column(name = "created_at", nullable = false, updatable = false) private Instant createdAt;
    @Column(name = "updated_at", nullable = false) private Instant updatedAt;
    @PrePersist protected void onCreate() { createdAt = Instant.now(); updatedAt = Instant.now(); }
    @PreUpdate protected void onUpdate() { updatedAt = Instant.now(); }

    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public StudentProfile getStudent() { return student; } public void setStudent(StudentProfile v) { this.student = v; }
    public FacultyProfile getFaculty() { return faculty; } public void setFaculty(FacultyProfile v) { this.faculty = v; }
    public TransportRoute getRoute() { return route; } public void setRoute(TransportRoute v) { this.route = v; }
    public LocalDate getValidFrom() { return validFrom; } public void setValidFrom(LocalDate v) { this.validFrom = v; }
    public LocalDate getValidTo() { return validTo; } public void setValidTo(LocalDate v) { this.validTo = v; }
    public String getPassType() { return passType; } public void setPassType(String v) { this.passType = v; }
    public BigDecimal getFee() { return fee; } public void setFee(BigDecimal v) { this.fee = v; }
    public String getStatus() { return status; } public void setStatus(String v) { this.status = v; }
    public Instant getCreatedAt() { return createdAt; } public Instant getUpdatedAt() { return updatedAt; }
}
