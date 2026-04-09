package com.cms.model;

import java.time.Instant;
import jakarta.persistence.*;

@Entity
@Table(name = "faculty_workloads")
public class FacultyWorkload {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "faculty_id", nullable = false) private FacultyProfile faculty;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "semester_id", nullable = false) private Semester semester;
    @Column(name = "teaching_hours") private int teachingHours;
    @Column(name = "lab_hours") private int labHours;
    @Column(name = "admin_hours") private int adminHours;
    @Column(name = "research_hours") private int researchHours;
    @Column(name = "created_at", nullable = false, updatable = false) private Instant createdAt;
    @Column(name = "updated_at", nullable = false) private Instant updatedAt;
    @PrePersist protected void onCreate() { createdAt = Instant.now(); updatedAt = Instant.now(); }
    @PreUpdate protected void onUpdate() { updatedAt = Instant.now(); }

    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public FacultyProfile getFaculty() { return faculty; } public void setFaculty(FacultyProfile v) { this.faculty = v; }
    public Semester getSemester() { return semester; } public void setSemester(Semester v) { this.semester = v; }
    public int getTeachingHours() { return teachingHours; } public void setTeachingHours(int v) { this.teachingHours = v; }
    public int getLabHours() { return labHours; } public void setLabHours(int v) { this.labHours = v; }
    public int getAdminHours() { return adminHours; } public void setAdminHours(int v) { this.adminHours = v; }
    public int getResearchHours() { return researchHours; } public void setResearchHours(int v) { this.researchHours = v; }
    public Instant getCreatedAt() { return createdAt; } public Instant getUpdatedAt() { return updatedAt; }
}
