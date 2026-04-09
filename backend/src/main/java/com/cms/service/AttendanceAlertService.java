package com.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cms.dto.AttendanceAlertRequest;
import com.cms.dto.AttendanceAlertResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.AttendanceAlert;
import com.cms.model.Course;
import com.cms.model.StudentProfile;
import com.cms.repository.AttendanceAlertRepository;
import com.cms.repository.CourseRepository;
import com.cms.repository.StudentProfileRepository;

@Service
public class AttendanceAlertService {

    private final AttendanceAlertRepository attendanceAlertRepository;
    private final StudentProfileRepository studentProfileRepository;
    private final CourseRepository courseRepository;

    public AttendanceAlertService(AttendanceAlertRepository attendanceAlertRepository,
                                  StudentProfileRepository studentProfileRepository,
                                  CourseRepository courseRepository) {
        this.attendanceAlertRepository = attendanceAlertRepository;
        this.studentProfileRepository = studentProfileRepository;
        this.courseRepository = courseRepository;
    }

    public List<AttendanceAlertResponse> findAll() {
        return attendanceAlertRepository.findAll().stream()
            .map(this::toResponse)
            .toList();
    }

    public AttendanceAlertResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    public AttendanceAlertResponse create(AttendanceAlertRequest request) {
        StudentProfile student = studentProfileRepository.findById(request.studentId())
            .orElseThrow(() -> new ResourceNotFoundException("StudentProfile", request.studentId()));
        Course course = courseRepository.findById(request.courseId())
            .orElseThrow(() -> new ResourceNotFoundException("Course", request.courseId()));

        AttendanceAlert alert = new AttendanceAlert();
        mapRequestToEntity(alert, request, student, course);
        alert.setResolved(false);
        return toResponse(attendanceAlertRepository.save(alert));
    }

    public AttendanceAlertResponse update(Long id, AttendanceAlertRequest request) {
        AttendanceAlert alert = getOrThrow(id);
        StudentProfile student = studentProfileRepository.findById(request.studentId())
            .orElseThrow(() -> new ResourceNotFoundException("StudentProfile", request.studentId()));
        Course course = courseRepository.findById(request.courseId())
            .orElseThrow(() -> new ResourceNotFoundException("Course", request.courseId()));

        mapRequestToEntity(alert, request, student, course);
        return toResponse(attendanceAlertRepository.save(alert));
    }

    public AttendanceAlertResponse resolve(Long id) {
        AttendanceAlert alert = getOrThrow(id);
        alert.setResolved(true);
        return toResponse(attendanceAlertRepository.save(alert));
    }

    public void delete(Long id) {
        if (!attendanceAlertRepository.existsById(id)) {
            throw new ResourceNotFoundException("AttendanceAlert", id);
        }
        attendanceAlertRepository.deleteById(id);
    }

    private void mapRequestToEntity(AttendanceAlert alert, AttendanceAlertRequest request,
                                    StudentProfile student, Course course) {
        alert.setStudent(student);
        alert.setCourse(course);
        alert.setAlertType(request.alertType());
        alert.setMessage(request.message());
        alert.setThresholdPercentage(request.thresholdPercentage());
        alert.setCurrentPercentage(request.currentPercentage());
    }

    private AttendanceAlert getOrThrow(Long id) {
        return attendanceAlertRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("AttendanceAlert", id));
    }

    private AttendanceAlertResponse toResponse(AttendanceAlert a) {
        return new AttendanceAlertResponse(
            a.getId(),
            a.getStudent().getId(),
            a.getStudent().getFirstName() + " " + a.getStudent().getLastName(),
            a.getCourse().getId(), a.getCourse().getName(),
            a.getAlertType(), a.getMessage(),
            a.getThresholdPercentage(), a.getCurrentPercentage(),
            a.getResolved(),
            a.getCreatedAt(), a.getUpdatedAt()
        );
    }
}
