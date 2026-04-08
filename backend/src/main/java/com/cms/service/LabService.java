package com.cms.service;

import com.cms.dto.LabRequest;
import com.cms.dto.LabResponse;
import com.cms.dto.LabSummaryResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.Department;
import com.cms.model.Lab;
import com.cms.model.LabType;
import com.cms.model.enums.LabStatus;
import com.cms.repository.DepartmentRepository;
import com.cms.repository.LabRepository;
import com.cms.repository.LabTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LabService {

    private final LabRepository labRepository;
    private final LabTypeRepository labTypeRepository;
    private final DepartmentRepository departmentRepository;

    public LabService(LabRepository labRepository,
                      LabTypeRepository labTypeRepository,
                      DepartmentRepository departmentRepository) {
        this.labRepository = labRepository;
        this.labTypeRepository = labTypeRepository;
        this.departmentRepository = departmentRepository;
    }

    @Transactional(readOnly = true)
    public List<LabResponse> findAll() {
        return labRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public LabResponse findById(Long id) {
        Lab lab = labRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lab", id));
        return toResponse(lab);
    }

    @Transactional(readOnly = true)
    public List<LabResponse> findByDepartmentId(Long departmentId) {
        return labRepository.findByDepartmentId(departmentId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public LabResponse create(LabRequest request) {
        LabType labType = labTypeRepository.findById(request.labTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("LabType", request.labTypeId()));
        Department department = departmentRepository.findById(request.departmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department", request.departmentId()));

        Lab lab = new Lab();
        lab.setName(request.name());
        lab.setLabType(labType);
        lab.setDepartment(department);
        lab.setBuilding(request.building());
        lab.setFloor(request.floor());
        lab.setRoomNumber(request.roomNumber());
        lab.setCapacity(request.capacity() != null ? request.capacity() : 0);
        lab.setDescription(request.description());
        lab.setStatus(request.status() != null ? request.status() : LabStatus.ACTIVE);
        lab.setActive(request.active() != null ? request.active() : true);
        Lab saved = labRepository.save(lab);
        return toResponse(saved);
    }

    @Transactional
    public LabResponse update(Long id, LabRequest request) {
        Lab lab = labRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lab", id));
        LabType labType = labTypeRepository.findById(request.labTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("LabType", request.labTypeId()));
        Department department = departmentRepository.findById(request.departmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department", request.departmentId()));

        lab.setName(request.name());
        lab.setLabType(labType);
        lab.setDepartment(department);
        lab.setBuilding(request.building());
        lab.setFloor(request.floor());
        lab.setRoomNumber(request.roomNumber());
        if (request.capacity() != null) {
            lab.setCapacity(request.capacity());
        }
        lab.setDescription(request.description());
        if (request.status() != null) {
            lab.setStatus(request.status());
        }
        if (request.active() != null) {
            lab.setActive(request.active());
        }
        Lab saved = labRepository.save(lab);
        return toResponse(saved);
    }

    @Transactional
    public void delete(Long id) {
        Lab lab = labRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lab", id));
        labRepository.delete(lab);
    }

    @Transactional(readOnly = true)
    public LabSummaryResponse getSummary() {
        long totalLabs = labRepository.count();
        long activeLabs = labRepository.countByActiveTrue();
        long underMaintenance = labRepository.countByStatus(LabStatus.UNDER_MAINTENANCE);
        long inactive = totalLabs - activeLabs;
        return new LabSummaryResponse(totalLabs, activeLabs, underMaintenance, inactive);
    }

    private LabResponse toResponse(Lab entity) {
        return new LabResponse(
                entity.getId(),
                entity.getName(),
                entity.getLabType().getId(),
                entity.getLabType().getName(),
                entity.getDepartment().getId(),
                entity.getDepartment().getName(),
                entity.getBuilding(),
                entity.getFloor(),
                entity.getRoomNumber(),
                entity.getCapacity(),
                entity.getDescription(),
                entity.getStatus(),
                entity.isActive()
        );
    }
}
