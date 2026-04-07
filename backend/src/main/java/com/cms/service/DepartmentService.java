package com.cms.service;

import com.cms.dto.DepartmentRequest;
import com.cms.dto.DepartmentResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.Department;
import com.cms.repository.DepartmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Transactional(readOnly = true)
    public List<DepartmentResponse> findAll() {
        return departmentRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public DepartmentResponse findById(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department", id));
        return toResponse(department);
    }

    @Transactional
    public DepartmentResponse create(DepartmentRequest request) {
        if (departmentRepository.existsByCode(request.code())) {
            throw new IllegalArgumentException("Department with code '" + request.code() + "' already exists");
        }
        Department department = new Department();
        department.setName(request.name());
        department.setCode(request.code());
        department.setHeadName(request.headName());
        department.setDescription(request.description());
        department.setActive(request.active() != null ? request.active() : true);
        Department saved = departmentRepository.save(department);
        return toResponse(saved);
    }

    @Transactional
    public DepartmentResponse update(Long id, DepartmentRequest request) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department", id));
        if (!department.getCode().equals(request.code()) && departmentRepository.existsByCode(request.code())) {
            throw new IllegalArgumentException("Department with code '" + request.code() + "' already exists");
        }
        department.setName(request.name());
        department.setCode(request.code());
        department.setHeadName(request.headName());
        department.setDescription(request.description());
        if (request.active() != null) {
            department.setActive(request.active());
        }
        Department saved = departmentRepository.save(department);
        return toResponse(saved);
    }

    @Transactional
    public void delete(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department", id));
        departmentRepository.delete(department);
    }

    private DepartmentResponse toResponse(Department entity) {
        return new DepartmentResponse(
                entity.getId(),
                entity.getName(),
                entity.getCode(),
                entity.getHeadName(),
                entity.getDescription(),
                entity.isActive()
        );
    }
}
