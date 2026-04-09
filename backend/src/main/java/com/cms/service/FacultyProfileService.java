package com.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cms.dto.FacultyProfileRequest;
import com.cms.dto.FacultyProfileResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.Department;
import com.cms.model.FacultyProfile;
import com.cms.repository.DepartmentRepository;
import com.cms.repository.FacultyProfileRepository;

@Service
public class FacultyProfileService {

    private final FacultyProfileRepository facultyProfileRepository;
    private final DepartmentRepository departmentRepository;

    public FacultyProfileService(FacultyProfileRepository facultyProfileRepository,
                                 DepartmentRepository departmentRepository) {
        this.facultyProfileRepository = facultyProfileRepository;
        this.departmentRepository = departmentRepository;
    }

    public List<FacultyProfileResponse> findAll() {
        return facultyProfileRepository.findAll().stream()
            .map(this::toResponse)
            .toList();
    }

    public FacultyProfileResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    public FacultyProfileResponse create(FacultyProfileRequest request) {
        Department department = departmentRepository.findById(request.departmentId())
            .orElseThrow(() -> new ResourceNotFoundException("Department", request.departmentId()));
        FacultyProfile fp = new FacultyProfile();
        fp.setKeycloakId(request.keycloakId());
        fp.setEmployeeCode(request.employeeCode());
        fp.setFirstName(request.firstName());
        fp.setLastName(request.lastName());
        fp.setEmail(request.email());
        fp.setPhone(request.phone());
        fp.setDepartment(department);
        fp.setDesignation(request.designation());
        fp.setJoiningDate(request.joiningDate());
        fp.setStatus(request.status() != null ? request.status() : "ACTIVE");
        return toResponse(facultyProfileRepository.save(fp));
    }

    public FacultyProfileResponse update(Long id, FacultyProfileRequest request) {
        FacultyProfile fp = getOrThrow(id);
        Department department = departmentRepository.findById(request.departmentId())
            .orElseThrow(() -> new ResourceNotFoundException("Department", request.departmentId()));
        fp.setKeycloakId(request.keycloakId());
        fp.setEmployeeCode(request.employeeCode());
        fp.setFirstName(request.firstName());
        fp.setLastName(request.lastName());
        fp.setEmail(request.email());
        fp.setPhone(request.phone());
        fp.setDepartment(department);
        fp.setDesignation(request.designation());
        fp.setJoiningDate(request.joiningDate());
        if (request.status() != null) {
            fp.setStatus(request.status());
        }
        return toResponse(facultyProfileRepository.save(fp));
    }

    public void delete(Long id) {
        if (!facultyProfileRepository.existsById(id)) {
            throw new ResourceNotFoundException("FacultyProfile", id);
        }
        facultyProfileRepository.deleteById(id);
    }

    private FacultyProfile getOrThrow(Long id) {
        return facultyProfileRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("FacultyProfile", id));
    }

    private FacultyProfileResponse toResponse(FacultyProfile fp) {
        return new FacultyProfileResponse(
            fp.getId(), fp.getKeycloakId(), fp.getEmployeeCode(),
            fp.getFirstName(), fp.getLastName(), fp.getEmail(), fp.getPhone(),
            fp.getDepartment().getId(), fp.getDepartment().getName(),
            fp.getDesignation(), fp.getJoiningDate(), fp.getStatus(),
            fp.getCreatedAt(), fp.getUpdatedAt()
        );
    }
}
