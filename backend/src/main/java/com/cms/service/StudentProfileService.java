package com.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cms.dto.StudentProfileRequest;
import com.cms.dto.StudentProfileResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.Department;
import com.cms.model.Program;
import com.cms.model.StudentProfile;
import com.cms.repository.DepartmentRepository;
import com.cms.repository.ProgramRepository;
import com.cms.repository.StudentProfileRepository;

@Service
public class StudentProfileService {

    private final StudentProfileRepository studentProfileRepository;
    private final ProgramRepository programRepository;
    private final DepartmentRepository departmentRepository;

    public StudentProfileService(StudentProfileRepository studentProfileRepository,
                                 ProgramRepository programRepository,
                                 DepartmentRepository departmentRepository) {
        this.studentProfileRepository = studentProfileRepository;
        this.programRepository = programRepository;
        this.departmentRepository = departmentRepository;
    }

    public List<StudentProfileResponse> findAll() {
        return studentProfileRepository.findAll().stream()
            .map(this::toResponse)
            .toList();
    }

    public StudentProfileResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    public StudentProfileResponse create(StudentProfileRequest request) {
        if (studentProfileRepository.existsByEnrollmentNumber(request.enrollmentNumber())) {
            throw new IllegalStateException("Enrollment number already exists: " + request.enrollmentNumber());
        }
        if (studentProfileRepository.existsByEmail(request.email())) {
            throw new IllegalStateException("Email already exists: " + request.email());
        }
        Program program = programRepository.findById(request.programId())
            .orElseThrow(() -> new ResourceNotFoundException("Program", request.programId()));
        Department department = departmentRepository.findById(request.departmentId())
            .orElseThrow(() -> new ResourceNotFoundException("Department", request.departmentId()));

        StudentProfile sp = new StudentProfile();
        mapRequestToEntity(sp, request, program, department);
        sp.setStatus(request.status() != null ? request.status() : "ACTIVE");
        return toResponse(studentProfileRepository.save(sp));
    }

    public StudentProfileResponse update(Long id, StudentProfileRequest request) {
        StudentProfile sp = getOrThrow(id);
        studentProfileRepository.findByEnrollmentNumber(request.enrollmentNumber())
            .filter(existing -> !existing.getId().equals(id))
            .ifPresent(existing -> {
                throw new IllegalStateException("Enrollment number already exists: " + request.enrollmentNumber());
            });
        studentProfileRepository.findByEmail(request.email())
            .filter(existing -> !existing.getId().equals(id))
            .ifPresent(existing -> {
                throw new IllegalStateException("Email already exists: " + request.email());
            });
        Program program = programRepository.findById(request.programId())
            .orElseThrow(() -> new ResourceNotFoundException("Program", request.programId()));
        Department department = departmentRepository.findById(request.departmentId())
            .orElseThrow(() -> new ResourceNotFoundException("Department", request.departmentId()));

        mapRequestToEntity(sp, request, program, department);
        if (request.status() != null) {
            sp.setStatus(request.status());
        }
        return toResponse(studentProfileRepository.save(sp));
    }

    public void delete(Long id) {
        if (!studentProfileRepository.existsById(id)) {
            throw new ResourceNotFoundException("StudentProfile", id);
        }
        studentProfileRepository.deleteById(id);
    }

    private void mapRequestToEntity(StudentProfile sp, StudentProfileRequest request,
                                    Program program, Department department) {
        sp.setKeycloakId(request.keycloakId());
        sp.setEnrollmentNumber(request.enrollmentNumber());
        sp.setFirstName(request.firstName());
        sp.setLastName(request.lastName());
        sp.setEmail(request.email());
        sp.setPhone(request.phone());
        sp.setProgram(program);
        sp.setDepartment(department);
        sp.setCurrentSemester(request.currentSemester());
        sp.setAdmissionDate(request.admissionDate());
    }

    private StudentProfile getOrThrow(Long id) {
        return studentProfileRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("StudentProfile", id));
    }

    private StudentProfileResponse toResponse(StudentProfile sp) {
        return new StudentProfileResponse(
            sp.getId(), sp.getKeycloakId(), sp.getEnrollmentNumber(),
            sp.getFirstName(), sp.getLastName(), sp.getEmail(), sp.getPhone(),
            sp.getProgram().getId(), sp.getProgram().getName(),
            sp.getDepartment().getId(), sp.getDepartment().getName(),
            sp.getCurrentSemester(), sp.getAdmissionDate(), sp.getStatus(),
            sp.getCreatedAt(), sp.getUpdatedAt()
        );
    }
}
