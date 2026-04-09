package com.cms.model;

import java.time.LocalDate;
import java.time.Instant;
import jakarta.persistence.*;

@Entity
@Table(name = "committee_memberships")
public class CommitteeMembership {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "faculty_id", nullable = false) private FacultyProfile faculty;
    @Column(name = "committee_name", nullable = false, length = 200) private String committeeName;
    @Column(nullable = false, length = 100) private String role;
    @Column(name = "start_date", nullable = false) private LocalDate startDate;
    @Column(name = "end_date") private LocalDate endDate;
    @Column(nullable = false, length = 30) private String status = "ACTIVE";
    @Column(name = "created_at", nullable = false, updatable = false) private Instant createdAt;
    @Column(name = "updated_at", nullable = false) private Instant updatedAt;
    @PrePersist protected void onCreate() { createdAt = Instant.now(); updatedAt = Instant.now(); }
    @PreUpdate protected void onUpdate() { updatedAt = Instant.now(); }

    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public FacultyProfile getFaculty() { return faculty; } public void setFaculty(FacultyProfile v) { this.faculty = v; }
    public String getCommitteeName() { return committeeName; } public void setCommitteeName(String v) { this.committeeName = v; }
    public String getRole() { return role; } public void setRole(String v) { this.role = v; }
    public LocalDate getStartDate() { return startDate; } public void setStartDate(LocalDate v) { this.startDate = v; }
    public LocalDate getEndDate() { return endDate; } public void setEndDate(LocalDate v) { this.endDate = v; }
    public String getStatus() { return status; } public void setStatus(String v) { this.status = v; }
    public Instant getCreatedAt() { return createdAt; } public Instant getUpdatedAt() { return updatedAt; }
}
