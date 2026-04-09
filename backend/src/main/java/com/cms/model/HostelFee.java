package com.cms.model;

import java.math.BigDecimal;
import java.time.Instant;
import jakarta.persistence.*;

@Entity
@Table(name = "hostel_fees")
public class HostelFee {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "student_id", nullable = false) private StudentProfile student;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "hostel_id", nullable = false) private Hostel hostel;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "semester_id", nullable = false) private Semester semester;
    @Column(name = "room_fee", nullable = false, precision = 10, scale = 2) private BigDecimal roomFee;
    @Column(name = "mess_fee", precision = 10, scale = 2) private BigDecimal messFee;
    @Column(nullable = false, length = 30) private String status = "PENDING";
    @Column(name = "created_at", nullable = false, updatable = false) private Instant createdAt;
    @Column(name = "updated_at", nullable = false) private Instant updatedAt;
    @PrePersist protected void onCreate() { createdAt = Instant.now(); updatedAt = Instant.now(); }
    @PreUpdate protected void onUpdate() { updatedAt = Instant.now(); }

    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public StudentProfile getStudent() { return student; } public void setStudent(StudentProfile v) { this.student = v; }
    public Hostel getHostel() { return hostel; } public void setHostel(Hostel v) { this.hostel = v; }
    public Semester getSemester() { return semester; } public void setSemester(Semester v) { this.semester = v; }
    public BigDecimal getRoomFee() { return roomFee; } public void setRoomFee(BigDecimal v) { this.roomFee = v; }
    public BigDecimal getMessFee() { return messFee; } public void setMessFee(BigDecimal v) { this.messFee = v; }
    public String getStatus() { return status; } public void setStatus(String v) { this.status = v; }
    public Instant getCreatedAt() { return createdAt; } public Instant getUpdatedAt() { return updatedAt; }
}
