package com.cms.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cms.exception.ResourceNotFoundException;
import com.cms.model.TheoryAttendance;
import com.cms.repository.TheoryAttendanceRepository;

@Service
public class TheoryAttendanceService {

    private final TheoryAttendanceRepository theoryAttendanceRepository;

    public TheoryAttendanceService(TheoryAttendanceRepository theoryAttendanceRepository) {
        this.theoryAttendanceRepository = theoryAttendanceRepository;
    }

    public List<TheoryAttendance> findByStudentId(Long studentId) {
        return theoryAttendanceRepository.findByStudentId(studentId);
    }

    public List<TheoryAttendance> findByCourseIdAndDate(Long courseId, LocalDate date) {
        return theoryAttendanceRepository.findByCourseIdAndAttendanceDate(courseId, date);
    }

    public TheoryAttendance create(TheoryAttendance attendance) {
        return theoryAttendanceRepository.save(attendance);
    }

    public List<TheoryAttendance> createBulk(List<TheoryAttendance> attendances) {
        return theoryAttendanceRepository.saveAll(attendances);
    }

    public TheoryAttendance update(Long id, TheoryAttendance data) {
        TheoryAttendance attendance = theoryAttendanceRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("TheoryAttendance", id));
        attendance.setStatus(data.getStatus());
        attendance.setRemarks(data.getRemarks());
        return theoryAttendanceRepository.save(attendance);
    }

    public List<TheoryAttendance> findByStudentAndCourse(Long studentId, Long courseId) {
        return theoryAttendanceRepository.findByStudentIdAndCourseId(studentId, courseId);
    }

    public List<TheoryAttendance> findByStudentAndSemester(Long studentId, Long semesterId) {
        return theoryAttendanceRepository.findByStudentIdAndSemesterId(studentId, semesterId);
    }

    public List<TheoryAttendance> findByCourseAndDate(Long courseId, LocalDate date) {
        return theoryAttendanceRepository.findByCourseIdAndAttendanceDate(courseId, date);
    }

    public TheoryAttendance record(TheoryAttendance attendance) {
        return theoryAttendanceRepository.save(attendance);
    }

    public TheoryAttendance updateStatus(Long id, String status) {
        TheoryAttendance attendance = theoryAttendanceRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("TheoryAttendance", id));
        attendance.setStatus(status);
        return theoryAttendanceRepository.save(attendance);
    }
}
