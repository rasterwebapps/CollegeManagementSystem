package com.cms.service;

import com.cms.dto.FacultyProfileRequest;
import com.cms.dto.FacultyProfileResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.Department;
import com.cms.model.FacultyProfile;
import com.cms.repository.DepartmentRepository;
import com.cms.repository.FacultyProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FacultyProfileService {

    private final FacultyProfileRepository facultyProfileRepository;
    private final DepartmentRepository departmentRepository;

    public FacultyProfileService(FacultyProfileRepository facultyProfileRepository,
                                  DepartmentRepository departmentRepository) {
        this.facultyProfileRepository = facultyProfileRepository;
        this.departmentRepository = departmentRepository;
    }

    @Transactional(readOnly = true)
    public List<FacultyProfileResponse> findAll() {
        return facultyProfileRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public FacultyProfileResponse findById(Long id) {
        FacultyProfile profile = facultyProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("FacultyProfile", id));
        return toResponse(profile);
    }

    @Transactional(readOnly = true)
    public List<FacultyProfileResponse> findByDepartmentId(Long departmentId) {
        return facultyProfileRepository.findByDepartmentId(departmentId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public FacultyProfileResponse findByKeycloakUserId(String keycloakUserId) {
        FacultyProfile profile = facultyProfileRepository.findByKeycloakUserId(keycloakUserId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "FacultyProfile not found with keycloakUserId: " + keycloakUserId));
        return toResponse(profile);
    }

    @Transactional
    public FacultyProfileResponse create(FacultyProfileRequest request) {
        if (facultyProfileRepository.existsByKeycloakUserId(request.keycloakUserId())) {
            throw new IllegalArgumentException(
                    "Faculty profile with keycloakUserId '" + request.keycloakUserId() + "' already exists");
        }
        if (facultyProfileRepository.existsByEmployeeId(request.employeeId())) {
            throw new IllegalArgumentException(
                    "Faculty profile with employeeId '" + request.employeeId() + "' already exists");
        }
        Department department = departmentRepository.findById(request.departmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department", request.departmentId()));

        FacultyProfile profile = new FacultyProfile();
        profile.setKeycloakUserId(request.keycloakUserId());
        profile.setEmployeeId(request.employeeId());
        profile.setDepartment(department);
        profile.setDesignation(request.designation());
        profile.setQualification(request.qualification());
        profile.setSpecialization(request.specialization());
        profile.setJoiningDate(request.joiningDate());
        profile.setPhone(request.phone());
        profile.setActive(request.active() != null ? request.active() : true);
        FacultyProfile saved = facultyProfileRepository.save(profile);
        return toResponse(saved);
    }

    @Transactional
    public FacultyProfileResponse update(Long id, FacultyProfileRequest request) {
        FacultyProfile profile = facultyProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("FacultyProfile", id));
        if (!profile.getKeycloakUserId().equals(request.keycloakUserId())
                && facultyProfileRepository.existsByKeycloakUserId(request.keycloakUserId())) {
            throw new IllegalArgumentException(
                    "Faculty profile with keycloakUserId '" + request.keycloakUserId() + "' already exists");
        }
        if (!profile.getEmployeeId().equals(request.employeeId())
                && facultyProfileRepository.existsByEmployeeId(request.employeeId())) {
            throw new IllegalArgumentException(
                    "Faculty profile with employeeId '" + request.employeeId() + "' already exists");
        }
        Department department = departmentRepository.findById(request.departmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department", request.departmentId()));

        profile.setKeycloakUserId(request.keycloakUserId());
        profile.setEmployeeId(request.employeeId());
        profile.setDepartment(department);
        profile.setDesignation(request.designation());
        profile.setQualification(request.qualification());
        profile.setSpecialization(request.specialization());
        profile.setJoiningDate(request.joiningDate());
        profile.setPhone(request.phone());
        if (request.active() != null) {
            profile.setActive(request.active());
        }
        FacultyProfile saved = facultyProfileRepository.save(profile);
        return toResponse(saved);
    }

    @Transactional
    public void delete(Long id) {
        FacultyProfile profile = facultyProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("FacultyProfile", id));
        facultyProfileRepository.delete(profile);
    }

    private FacultyProfileResponse toResponse(FacultyProfile entity) {
        return new FacultyProfileResponse(
                entity.getId(),
                entity.getKeycloakUserId(),
                entity.getEmployeeId(),
                entity.getDepartment().getId(),
                entity.getDepartment().getName(),
                entity.getDesignation(),
                entity.getQualification(),
                entity.getSpecialization(),
                entity.getJoiningDate(),
                entity.getPhone(),
                entity.isActive()
        );
    }
}
