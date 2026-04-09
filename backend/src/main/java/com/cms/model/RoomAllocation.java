package com.cms.model;

import java.time.LocalTime;
import java.time.Instant;
import jakarta.persistence.*;

@Entity
@Table(name = "room_allocations")
public class RoomAllocation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "room_id", nullable = false) private RoomResource room;
    @Column(nullable = false, length = 100) private String purpose;
    @Column(name = "day_of_week", length = 20) private String dayOfWeek;
    @Column(name = "start_time") private LocalTime startTime;
    @Column(name = "end_time") private LocalTime endTime;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "course_id") private Course course;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "semester_id") private Semester semester;
    @Column(name = "created_at", nullable = false, updatable = false) private Instant createdAt;
    @PrePersist protected void onCreate() { createdAt = Instant.now(); }

    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public RoomResource getRoom() { return room; } public void setRoom(RoomResource v) { this.room = v; }
    public String getPurpose() { return purpose; } public void setPurpose(String v) { this.purpose = v; }
    public String getDayOfWeek() { return dayOfWeek; } public void setDayOfWeek(String v) { this.dayOfWeek = v; }
    public LocalTime getStartTime() { return startTime; } public void setStartTime(LocalTime v) { this.startTime = v; }
    public LocalTime getEndTime() { return endTime; } public void setEndTime(LocalTime v) { this.endTime = v; }
    public Course getCourse() { return course; } public void setCourse(Course v) { this.course = v; }
    public Semester getSemester() { return semester; } public void setSemester(Semester v) { this.semester = v; }
    public Instant getCreatedAt() { return createdAt; }
}
