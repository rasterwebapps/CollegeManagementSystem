package com.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cms.dto.EquipmentRequest;
import com.cms.dto.EquipmentResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.Equipment;
import com.cms.model.Lab;
import com.cms.repository.EquipmentRepository;
import com.cms.repository.LabRepository;

@Service
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final LabRepository labRepository;

    public EquipmentService(EquipmentRepository equipmentRepository, LabRepository labRepository) {
        this.equipmentRepository = equipmentRepository;
        this.labRepository = labRepository;
    }

    public List<EquipmentResponse> findAll() {
        return equipmentRepository.findAll().stream().map(this::toResponse).toList();
    }

    public EquipmentResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    public EquipmentResponse create(EquipmentRequest request) {
        Lab lab = labRepository.findById(request.labId())
            .orElseThrow(() -> new ResourceNotFoundException("Lab", request.labId()));
        Equipment e = new Equipment();
        e.setLab(lab);
        e.setName(request.name());
        e.setModelNumber(request.modelNumber());
        e.setSerialNumber(request.serialNumber());
        e.setPurchaseDate(request.purchaseDate());
        e.setPurchaseCost(request.purchaseCost());
        e.setWarrantyExpiry(request.warrantyExpiry());
        e.setStatus(request.status() != null ? request.status() : "OPERATIONAL");
        return toResponse(equipmentRepository.save(e));
    }

    public EquipmentResponse update(Long id, EquipmentRequest request) {
        Equipment e = getOrThrow(id);
        Lab lab = labRepository.findById(request.labId())
            .orElseThrow(() -> new ResourceNotFoundException("Lab", request.labId()));
        e.setLab(lab);
        e.setName(request.name());
        e.setModelNumber(request.modelNumber());
        e.setSerialNumber(request.serialNumber());
        e.setPurchaseDate(request.purchaseDate());
        e.setPurchaseCost(request.purchaseCost());
        e.setWarrantyExpiry(request.warrantyExpiry());
        if (request.status() != null) { e.setStatus(request.status()); }
        return toResponse(equipmentRepository.save(e));
    }

    public void delete(Long id) {
        if (!equipmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Equipment", id);
        }
        equipmentRepository.deleteById(id);
    }

    private Equipment getOrThrow(Long id) {
        return equipmentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Equipment", id));
    }

    private EquipmentResponse toResponse(Equipment e) {
        return new EquipmentResponse(
            e.getId(), e.getLab().getId(), e.getLab().getName(),
            e.getName(), e.getModelNumber(), e.getSerialNumber(),
            e.getPurchaseDate(), e.getPurchaseCost(), e.getWarrantyExpiry(),
            e.getStatus(), e.getCreatedAt(), e.getUpdatedAt()
        );
    }
}
