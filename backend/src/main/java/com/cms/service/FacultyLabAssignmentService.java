package com.cms.service;

import com.cms.dto.FacultyLabAssignmentRequest;
import com.cms.dto.FacultyLabAssignmentResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.AcademicYear;
import com.cms.model.Course;
import com.cms.model.FacultyLabAssignment;
import com.cms.model.FacultyProfile;
import com.cms.model.Lab;
import com.cms.model.Semester;
import com.cms.repository.AcademicYearRepository;
import com.cms.repository.CourseRepository;
import com.cms.repository.FacultyLabAssignmentRepository;
import com.cms.repository.FacultyProfileRepository;
import com.cms.repository.LabRepository;
import com.cms.repository.SemesterRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FacultyLabAssignmentService {

    private final FacultyLabAssignmentRepository assignmentRepository;
    private final FacultyProfileRepository facultyProfileRepository;
    private final LabRepository labRepository;
    private final CourseRepository courseRepository;
    private final AcademicYearRepository academicYearRepository;
    private final SemesterRepository semesterRepository;

    public FacultyLabAssignmentService(FacultyLabAssignmentRepository assignmentRepository,
                                        FacultyProfileRepository facultyProfileRepository,
                                        LabRepository labRepository,
                                        CourseRepository courseRepository,
                                        AcademicYearRepository academicYearRepository,
                                        SemesterRepository semesterRepository) {
        this.assignmentRepository = assignmentRepository;
        this.facultyProfileRepository = facultyProfileRepository;
        this.labRepository = labRepository;
        this.courseRepository = courseRepository;
        this.academicYearRepository = academicYearRepository;
        this.semesterRepository = semesterRepository;
    }

    @Transactional(readOnly = true)
    public List<FacultyLabAssignmentResponse> findByFacultyId(Long facultyId) {
        return assignmentRepository.findByFacultyId(facultyId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<FacultyLabAssignmentResponse> findByLabId(Long labId) {
        return assignmentRepository.findByLabId(labId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public FacultyLabAssignmentResponse findById(Long id) {
        FacultyLabAssignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("FacultyLabAssignment", id));
        return toResponse(assignment);
    }

    @Transactional
    public FacultyLabAssignmentResponse create(FacultyLabAssignmentRequest request) {
        FacultyProfile faculty = facultyProfileRepository.findById(request.facultyId())
                .orElseThrow(() -> new ResourceNotFoundException("FacultyProfile", request.facultyId()));
        Lab lab = labRepository.findById(request.labId())
                .orElseThrow(() -> new ResourceNotFoundException("Lab", request.labId()));
        Course course = courseRepository.findById(request.courseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", request.courseId()));
        AcademicYear academicYear = academicYearRepository.findById(request.academicYearId())
                .orElseThrow(() -> new ResourceNotFoundException("AcademicYear", request.academicYearId()));
        Semester semester = semesterRepository.findById(request.semesterId())
                .orElseThrow(() -> new ResourceNotFoundException("Semester", request.semesterId()));

        FacultyLabAssignment assignment = new FacultyLabAssignment();
        assignment.setFaculty(faculty);
        assignment.setLab(lab);
        assignment.setCourse(course);
        assignment.setAcademicYear(academicYear);
        assignment.setSemester(semester);
        FacultyLabAssignment saved = assignmentRepository.save(assignment);
        return toResponse(saved);
    }

    @Transactional
    public void delete(Long id) {
        FacultyLabAssignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("FacultyLabAssignment", id));
        assignmentRepository.delete(assignment);
    }

    private FacultyLabAssignmentResponse toResponse(FacultyLabAssignment entity) {
        return new FacultyLabAssignmentResponse(
                entity.getId(),
                entity.getFaculty().getId(),
                entity.getFaculty().getEmployeeId(),
                entity.getLab().getId(),
                entity.getLab().getName(),
                entity.getCourse().getId(),
                entity.getCourse().getName(),
                entity.getAcademicYear().getId(),
                entity.getAcademicYear().getName(),
                entity.getSemester().getId(),
                entity.getSemester().getName()
        );
    }
}
