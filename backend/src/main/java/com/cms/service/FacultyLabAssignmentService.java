package com.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cms.dto.FacultyLabAssignmentRequest;
import com.cms.dto.FacultyLabAssignmentResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.FacultyLabAssignment;
import com.cms.model.FacultyProfile;
import com.cms.model.Lab;
import com.cms.model.Semester;
import com.cms.repository.FacultyLabAssignmentRepository;
import com.cms.repository.FacultyProfileRepository;
import com.cms.repository.LabRepository;
import com.cms.repository.SemesterRepository;

@Service
public class FacultyLabAssignmentService {

    private final FacultyLabAssignmentRepository assignmentRepository;
    private final FacultyProfileRepository facultyProfileRepository;
    private final LabRepository labRepository;
    private final SemesterRepository semesterRepository;

    public FacultyLabAssignmentService(FacultyLabAssignmentRepository assignmentRepository,
                                       FacultyProfileRepository facultyProfileRepository,
                                       LabRepository labRepository,
                                       SemesterRepository semesterRepository) {
        this.assignmentRepository = assignmentRepository;
        this.facultyProfileRepository = facultyProfileRepository;
        this.labRepository = labRepository;
        this.semesterRepository = semesterRepository;
    }

    public List<FacultyLabAssignmentResponse> findAll() {
        return assignmentRepository.findAll().stream()
            .map(this::toResponse)
            .toList();
    }

    public FacultyLabAssignmentResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    public FacultyLabAssignmentResponse create(FacultyLabAssignmentRequest request) {
        FacultyProfile faculty = facultyProfileRepository.findById(request.facultyId())
            .orElseThrow(() -> new ResourceNotFoundException("FacultyProfile", request.facultyId()));
        Lab lab = labRepository.findById(request.labId())
            .orElseThrow(() -> new ResourceNotFoundException("Lab", request.labId()));
        Semester semester = semesterRepository.findById(request.semesterId())
            .orElseThrow(() -> new ResourceNotFoundException("Semester", request.semesterId()));
        FacultyLabAssignment assignment = new FacultyLabAssignment();
        assignment.setFaculty(faculty);
        assignment.setLab(lab);
        assignment.setSemester(semester);
        assignment.setAssignedDate(request.assignedDate());
        assignment.setActive(request.active() != null ? request.active() : true);
        return toResponse(assignmentRepository.save(assignment));
    }

    public FacultyLabAssignmentResponse update(Long id, FacultyLabAssignmentRequest request) {
        FacultyLabAssignment assignment = getOrThrow(id);
        FacultyProfile faculty = facultyProfileRepository.findById(request.facultyId())
            .orElseThrow(() -> new ResourceNotFoundException("FacultyProfile", request.facultyId()));
        Lab lab = labRepository.findById(request.labId())
            .orElseThrow(() -> new ResourceNotFoundException("Lab", request.labId()));
        Semester semester = semesterRepository.findById(request.semesterId())
            .orElseThrow(() -> new ResourceNotFoundException("Semester", request.semesterId()));
        assignment.setFaculty(faculty);
        assignment.setLab(lab);
        assignment.setSemester(semester);
        assignment.setAssignedDate(request.assignedDate());
        if (request.active() != null) {
            assignment.setActive(request.active());
        }
        return toResponse(assignmentRepository.save(assignment));
    }

    public void delete(Long id) {
        if (!assignmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("FacultyLabAssignment", id);
        }
        assignmentRepository.deleteById(id);
    }

    private FacultyLabAssignment getOrThrow(Long id) {
        return assignmentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("FacultyLabAssignment", id));
    }

    private FacultyLabAssignmentResponse toResponse(FacultyLabAssignment a) {
        return new FacultyLabAssignmentResponse(
            a.getId(), a.getFaculty().getId(),
            a.getFaculty().getFirstName() + " " + a.getFaculty().getLastName(),
            a.getLab().getId(), a.getLab().getName(),
            a.getSemester().getId(), a.getSemester().getName(),
            a.getAssignedDate(), a.getActive(),
            a.getCreatedAt(), a.getUpdatedAt()
        );
    }
}
