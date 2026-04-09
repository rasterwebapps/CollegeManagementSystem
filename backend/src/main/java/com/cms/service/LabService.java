package com.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cms.dto.LabRequest;
import com.cms.dto.LabResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.Department;
import com.cms.model.Lab;
import com.cms.model.LabType;
import com.cms.repository.DepartmentRepository;
import com.cms.repository.LabRepository;
import com.cms.repository.LabTypeRepository;

@Service
public class LabService {

    private final LabRepository labRepository;
    private final LabTypeRepository labTypeRepository;
    private final DepartmentRepository departmentRepository;

    public LabService(LabRepository labRepository, LabTypeRepository labTypeRepository,
                      DepartmentRepository departmentRepository) {
        this.labRepository = labRepository;
        this.labTypeRepository = labTypeRepository;
        this.departmentRepository = departmentRepository;
    }

    public List<LabResponse> findAll() {
        return labRepository.findAll().stream()
            .map(this::toResponse)
            .toList();
    }

    public LabResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    public LabResponse create(LabRequest request) {
        if (labRepository.existsByCode(request.code())) {
            throw new IllegalArgumentException("Lab with code '" + request.code() + "' already exists");
        }
        LabType labType = labTypeRepository.findById(request.labTypeId())
            .orElseThrow(() -> new ResourceNotFoundException("LabType", request.labTypeId()));
        Department department = departmentRepository.findById(request.departmentId())
            .orElseThrow(() -> new ResourceNotFoundException("Department", request.departmentId()));
        Lab lab = new Lab();
        lab.setName(request.name());
        lab.setCode(request.code());
        lab.setLabType(labType);
        lab.setDepartment(department);
        lab.setLocation(request.location());
        lab.setCapacity(request.capacity());
        lab.setStatus(request.status() != null ? request.status() : "AVAILABLE");
        return toResponse(labRepository.save(lab));
    }

    public LabResponse update(Long id, LabRequest request) {
        Lab lab = getOrThrow(id);
        labRepository.findByCode(request.code())
            .filter(l -> !l.getId().equals(id))
            .ifPresent(l -> {
                throw new IllegalArgumentException("Lab with code '" + request.code() + "' already exists");
            });
        LabType labType = labTypeRepository.findById(request.labTypeId())
            .orElseThrow(() -> new ResourceNotFoundException("LabType", request.labTypeId()));
        Department department = departmentRepository.findById(request.departmentId())
            .orElseThrow(() -> new ResourceNotFoundException("Department", request.departmentId()));
        lab.setName(request.name());
        lab.setCode(request.code());
        lab.setLabType(labType);
        lab.setDepartment(department);
        lab.setLocation(request.location());
        lab.setCapacity(request.capacity());
        if (request.status() != null) {
            lab.setStatus(request.status());
        }
        return toResponse(labRepository.save(lab));
    }

    public void delete(Long id) {
        if (!labRepository.existsById(id)) {
            throw new ResourceNotFoundException("Lab", id);
        }
        labRepository.deleteById(id);
    }

    private Lab getOrThrow(Long id) {
        return labRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Lab", id));
    }

    private LabResponse toResponse(Lab l) {
        return new LabResponse(
            l.getId(), l.getName(), l.getCode(),
            l.getLabType().getId(), l.getLabType().getName(),
            l.getDepartment().getId(), l.getDepartment().getName(),
            l.getLocation(), l.getCapacity(), l.getStatus(),
            l.getCreatedAt(), l.getUpdatedAt()
        );
    }
}
