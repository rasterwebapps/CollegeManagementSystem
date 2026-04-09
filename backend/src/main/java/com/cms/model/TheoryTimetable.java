package com.cms.model;

import java.time.LocalTime;
import java.time.Instant;
import jakarta.persistence.*;

@Entity
@Table(name = "theory_timetables")
public class TheoryTimetable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "course_id", nullable = false) private Course course;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "faculty_id", nullable = false) private FacultyProfile faculty;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "room_id") private RoomResource room;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "semester_id", nullable = false) private Semester semester;
    @Column(name = "day_of_week", nullable = false, length = 20) private String dayOfWeek;
    @Column(name = "start_time", nullable = false) private LocalTime startTime;
    @Column(name = "end_time", nullable = false) private LocalTime endTime;
    @Column(name = "created_at", nullable = false, updatable = false) private Instant createdAt;
    @Column(name = "updated_at", nullable = false) private Instant updatedAt;
    @PrePersist protected void onCreate() { createdAt = Instant.now(); updatedAt = Instant.now(); }
    @PreUpdate protected void onUpdate() { updatedAt = Instant.now(); }

    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public Course getCourse() { return course; } public void setCourse(Course v) { this.course = v; }
    public FacultyProfile getFaculty() { return faculty; } public void setFaculty(FacultyProfile v) { this.faculty = v; }
    public RoomResource getRoom() { return room; } public void setRoom(RoomResource v) { this.room = v; }
    public Semester getSemester() { return semester; } public void setSemester(Semester v) { this.semester = v; }
    public String getDayOfWeek() { return dayOfWeek; } public void setDayOfWeek(String v) { this.dayOfWeek = v; }
    public LocalTime getStartTime() { return startTime; } public void setStartTime(LocalTime v) { this.startTime = v; }
    public LocalTime getEndTime() { return endTime; } public void setEndTime(LocalTime v) { this.endTime = v; }
    public Instant getCreatedAt() { return createdAt; } public Instant getUpdatedAt() { return updatedAt; }
}
