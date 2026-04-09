package com.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cms.dto.MaintenanceScheduleRequest;
import com.cms.dto.MaintenanceScheduleResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.Equipment;
import com.cms.model.MaintenanceSchedule;
import com.cms.repository.EquipmentRepository;
import com.cms.repository.MaintenanceScheduleRepository;

@Service
public class MaintenanceScheduleService {

    private final MaintenanceScheduleRepository maintenanceScheduleRepository;
    private final EquipmentRepository equipmentRepository;

    public MaintenanceScheduleService(MaintenanceScheduleRepository maintenanceScheduleRepository,
                                      EquipmentRepository equipmentRepository) {
        this.maintenanceScheduleRepository = maintenanceScheduleRepository;
        this.equipmentRepository = equipmentRepository;
    }

    public List<MaintenanceScheduleResponse> findAll() {
        return maintenanceScheduleRepository.findAll().stream().map(this::toResponse).toList();
    }

    public MaintenanceScheduleResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    public MaintenanceScheduleResponse create(MaintenanceScheduleRequest request) {
        Equipment equipment = equipmentRepository.findById(request.equipmentId())
            .orElseThrow(() -> new ResourceNotFoundException("Equipment", request.equipmentId()));
        MaintenanceSchedule ms = new MaintenanceSchedule();
        ms.setEquipment(equipment);
        ms.setMaintenanceType(request.maintenanceType());
        ms.setScheduledDate(request.scheduledDate());
        ms.setCompletedDate(request.completedDate());
        ms.setPerformedBy(request.performedBy());
        ms.setCost(request.cost());
        ms.setNotes(request.notes());
        ms.setStatus(request.status() != null ? request.status() : "SCHEDULED");
        return toResponse(maintenanceScheduleRepository.save(ms));
    }

    public MaintenanceScheduleResponse update(Long id, MaintenanceScheduleRequest request) {
        MaintenanceSchedule ms = getOrThrow(id);
        Equipment equipment = equipmentRepository.findById(request.equipmentId())
            .orElseThrow(() -> new ResourceNotFoundException("Equipment", request.equipmentId()));
        ms.setEquipment(equipment);
        ms.setMaintenanceType(request.maintenanceType());
        ms.setScheduledDate(request.scheduledDate());
        ms.setCompletedDate(request.completedDate());
        ms.setPerformedBy(request.performedBy());
        ms.setCost(request.cost());
        ms.setNotes(request.notes());
        if (request.status() != null) { ms.setStatus(request.status()); }
        return toResponse(maintenanceScheduleRepository.save(ms));
    }

    public void delete(Long id) {
        if (!maintenanceScheduleRepository.existsById(id)) {
            throw new ResourceNotFoundException("MaintenanceSchedule", id);
        }
        maintenanceScheduleRepository.deleteById(id);
    }

    private MaintenanceSchedule getOrThrow(Long id) {
        return maintenanceScheduleRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("MaintenanceSchedule", id));
    }

    private MaintenanceScheduleResponse toResponse(MaintenanceSchedule ms) {
        return new MaintenanceScheduleResponse(
            ms.getId(), ms.getEquipment().getId(), ms.getEquipment().getName(),
            ms.getMaintenanceType(), ms.getScheduledDate(), ms.getCompletedDate(),
            ms.getPerformedBy(), ms.getCost(), ms.getNotes(), ms.getStatus(),
            ms.getCreatedAt(), ms.getUpdatedAt()
        );
    }
}
