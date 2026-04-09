package com.cms.model;

import jakarta.persistence.*;

@Entity
@Table(name = "exam_seating_plans")
public class ExamSeatingPlan {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "exam_id", nullable = false) private ExamSchedule exam;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "student_id", nullable = false) private StudentProfile student;
    @Column(name = "room_number", length = 50) private String roomNumber;
    @Column(name = "seat_number", length = 20) private String seatNumber;

    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public ExamSchedule getExam() { return exam; } public void setExam(ExamSchedule v) { this.exam = v; }
    public StudentProfile getStudent() { return student; } public void setStudent(StudentProfile v) { this.student = v; }
    public String getRoomNumber() { return roomNumber; } public void setRoomNumber(String v) { this.roomNumber = v; }
    public String getSeatNumber() { return seatNumber; } public void setSeatNumber(String v) { this.seatNumber = v; }
}
