package com.cms.model;

import java.time.Instant;
import jakarta.persistence.*;

@Entity
@Table(name = "visitor_logs")
public class VisitorLog {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "student_id", nullable = false) private StudentProfile student;
    @Column(name = "visitor_name", nullable = false, length = 200) private String visitorName;
    @Column(nullable = false, length = 100) private String relation;
    @Column(columnDefinition = "TEXT") private String purpose;
    @Column(name = "in_time", nullable = false) private Instant inTime;
    @Column(name = "out_time") private Instant outTime;

    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public StudentProfile getStudent() { return student; } public void setStudent(StudentProfile v) { this.student = v; }
    public String getVisitorName() { return visitorName; } public void setVisitorName(String v) { this.visitorName = v; }
    public String getRelation() { return relation; } public void setRelation(String v) { this.relation = v; }
    public String getPurpose() { return purpose; } public void setPurpose(String v) { this.purpose = v; }
    public Instant getInTime() { return inTime; } public void setInTime(Instant v) { this.inTime = v; }
    public Instant getOutTime() { return outTime; } public void setOutTime(Instant v) { this.outTime = v; }
}
