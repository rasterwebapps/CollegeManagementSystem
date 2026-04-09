package com.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cms.dto.StudentEnrollmentRequest;
import com.cms.dto.StudentEnrollmentResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.Course;
import com.cms.model.Semester;
import com.cms.model.StudentEnrollment;
import com.cms.model.StudentProfile;
import com.cms.repository.CourseRepository;
import com.cms.repository.SemesterRepository;
import com.cms.repository.StudentEnrollmentRepository;
import com.cms.repository.StudentProfileRepository;

@Service
public class StudentEnrollmentService {

    private final StudentEnrollmentRepository studentEnrollmentRepository;
    private final StudentProfileRepository studentProfileRepository;
    private final CourseRepository courseRepository;
    private final SemesterRepository semesterRepository;

    public StudentEnrollmentService(StudentEnrollmentRepository studentEnrollmentRepository,
                                    StudentProfileRepository studentProfileRepository,
                                    CourseRepository courseRepository,
                                    SemesterRepository semesterRepository) {
        this.studentEnrollmentRepository = studentEnrollmentRepository;
        this.studentProfileRepository = studentProfileRepository;
        this.courseRepository = courseRepository;
        this.semesterRepository = semesterRepository;
    }

    public List<StudentEnrollmentResponse> findAll() {
        return studentEnrollmentRepository.findAll().stream()
            .map(this::toResponse)
            .toList();
    }

    public StudentEnrollmentResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    public StudentEnrollmentResponse create(StudentEnrollmentRequest request) {
        StudentProfile student = studentProfileRepository.findById(request.studentId())
            .orElseThrow(() -> new ResourceNotFoundException("StudentProfile", request.studentId()));
        Course course = courseRepository.findById(request.courseId())
            .orElseThrow(() -> new ResourceNotFoundException("Course", request.courseId()));
        Semester semester = semesterRepository.findById(request.semesterId())
            .orElseThrow(() -> new ResourceNotFoundException("Semester", request.semesterId()));

        StudentEnrollment se = new StudentEnrollment();
        mapRequestToEntity(se, request, student, course, semester);
        se.setStatus(request.status() != null ? request.status() : "ENROLLED");
        return toResponse(studentEnrollmentRepository.save(se));
    }

    public StudentEnrollmentResponse update(Long id, StudentEnrollmentRequest request) {
        StudentEnrollment se = getOrThrow(id);
        StudentProfile student = studentProfileRepository.findById(request.studentId())
            .orElseThrow(() -> new ResourceNotFoundException("StudentProfile", request.studentId()));
        Course course = courseRepository.findById(request.courseId())
            .orElseThrow(() -> new ResourceNotFoundException("Course", request.courseId()));
        Semester semester = semesterRepository.findById(request.semesterId())
            .orElseThrow(() -> new ResourceNotFoundException("Semester", request.semesterId()));

        mapRequestToEntity(se, request, student, course, semester);
        if (request.status() != null) {
            se.setStatus(request.status());
        }
        return toResponse(studentEnrollmentRepository.save(se));
    }

    public void delete(Long id) {
        if (!studentEnrollmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("StudentEnrollment", id);
        }
        studentEnrollmentRepository.deleteById(id);
    }

    private void mapRequestToEntity(StudentEnrollment se, StudentEnrollmentRequest request,
                                    StudentProfile student, Course course, Semester semester) {
        se.setStudent(student);
        se.setCourse(course);
        se.setSemester(semester);
        se.setLabBatchGroup(request.labBatchGroup());
        se.setEnrollmentDate(request.enrollmentDate());
    }

    private StudentEnrollment getOrThrow(Long id) {
        return studentEnrollmentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("StudentEnrollment", id));
    }

    private StudentEnrollmentResponse toResponse(StudentEnrollment se) {
        return new StudentEnrollmentResponse(
            se.getId(),
            se.getStudent().getId(),
            se.getStudent().getFirstName() + " " + se.getStudent().getLastName(),
            se.getCourse().getId(), se.getCourse().getName(),
            se.getSemester().getId(), se.getSemester().getName(),
            se.getLabBatchGroup(), se.getEnrollmentDate(), se.getStatus(),
            se.getCreatedAt(), se.getUpdatedAt()
        );
    }
}
