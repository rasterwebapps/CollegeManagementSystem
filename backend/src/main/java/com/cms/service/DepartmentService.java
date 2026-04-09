package com.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cms.dto.DepartmentRequest;
import com.cms.dto.DepartmentResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.Department;
import com.cms.repository.DepartmentRepository;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<DepartmentResponse> findAll() {
        return departmentRepository.findAll().stream()
            .map(this::toResponse)
            .toList();
    }

    public DepartmentResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    public DepartmentResponse create(DepartmentRequest request) {
        if (departmentRepository.existsByCode(request.code())) {
            throw new IllegalArgumentException("Department with code '" + request.code() + "' already exists");
        }
        Department department = new Department();
        department.setName(request.name());
        department.setCode(request.code());
        return toResponse(departmentRepository.save(department));
    }

    public DepartmentResponse update(Long id, DepartmentRequest request) {
        Department department = getOrThrow(id);
        departmentRepository.findByCode(request.code())
            .filter(d -> !d.getId().equals(id))
            .ifPresent(d -> {
                throw new IllegalArgumentException("Department with code '" + request.code() + "' already exists");
            });
        department.setName(request.name());
        department.setCode(request.code());
        return toResponse(departmentRepository.save(department));
    }

    public void delete(Long id) {
        if (!departmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Department", id);
        }
        departmentRepository.deleteById(id);
    }

    private Department getOrThrow(Long id) {
        return departmentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Department", id));
    }

    private DepartmentResponse toResponse(Department d) {
        return new DepartmentResponse(d.getId(), d.getName(), d.getCode(), d.getCreatedAt(), d.getUpdatedAt());
    }
}
