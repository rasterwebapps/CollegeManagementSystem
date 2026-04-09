package com.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cms.dto.AssetRequest;
import com.cms.dto.AssetResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.Asset;
import com.cms.model.Equipment;
import com.cms.repository.AssetRepository;
import com.cms.repository.EquipmentRepository;

@Service
public class AssetService {

    private final AssetRepository assetRepository;
    private final EquipmentRepository equipmentRepository;

    public AssetService(AssetRepository assetRepository, EquipmentRepository equipmentRepository) {
        this.assetRepository = assetRepository;
        this.equipmentRepository = equipmentRepository;
    }

    public List<AssetResponse> findAll() {
        return assetRepository.findAll().stream().map(this::toResponse).toList();
    }

    public AssetResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    public AssetResponse create(AssetRequest request) {
        if (assetRepository.existsByAssetCode(request.assetCode())) {
            throw new IllegalArgumentException("Asset code already exists: " + request.assetCode());
        }
        Asset a = new Asset();
        if (request.equipmentId() != null) {
            Equipment equipment = equipmentRepository.findById(request.equipmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Equipment", request.equipmentId()));
            a.setEquipment(equipment);
        }
        a.setAssetCode(request.assetCode());
        a.setName(request.name());
        a.setCategory(request.category());
        a.setPurchaseDate(request.purchaseDate());
        a.setPurchaseCost(request.purchaseCost());
        a.setCurrentValue(request.currentValue());
        a.setUsefulLifeYears(request.usefulLifeYears());
        a.setDepreciationMethod(request.depreciationMethod() != null ? request.depreciationMethod() : "STRAIGHT_LINE");
        a.setLocation(request.location());
        a.setStatus(request.status() != null ? request.status() : "ACTIVE");
        return toResponse(assetRepository.save(a));
    }

    public AssetResponse update(Long id, AssetRequest request) {
        Asset a = getOrThrow(id);
        if (!a.getAssetCode().equals(request.assetCode()) && assetRepository.existsByAssetCode(request.assetCode())) {
            throw new IllegalArgumentException("Asset code already exists: " + request.assetCode());
        }
        if (request.equipmentId() != null) {
            Equipment equipment = equipmentRepository.findById(request.equipmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Equipment", request.equipmentId()));
            a.setEquipment(equipment);
        } else {
            a.setEquipment(null);
        }
        a.setAssetCode(request.assetCode());
        a.setName(request.name());
        a.setCategory(request.category());
        a.setPurchaseDate(request.purchaseDate());
        a.setPurchaseCost(request.purchaseCost());
        a.setCurrentValue(request.currentValue());
        a.setUsefulLifeYears(request.usefulLifeYears());
        if (request.depreciationMethod() != null) { a.setDepreciationMethod(request.depreciationMethod()); }
        a.setLocation(request.location());
        if (request.status() != null) { a.setStatus(request.status()); }
        return toResponse(assetRepository.save(a));
    }

    public void delete(Long id) {
        if (!assetRepository.existsById(id)) {
            throw new ResourceNotFoundException("Asset", id);
        }
        assetRepository.deleteById(id);
    }

    private Asset getOrThrow(Long id) {
        return assetRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Asset", id));
    }

    private AssetResponse toResponse(Asset a) {
        return new AssetResponse(
            a.getId(), a.getEquipment() != null ? a.getEquipment().getId() : null,
            a.getEquipment() != null ? a.getEquipment().getName() : null,
            a.getAssetCode(), a.getName(), a.getCategory(),
            a.getPurchaseDate(), a.getPurchaseCost(), a.getCurrentValue(),
            a.getUsefulLifeYears(), a.getDepreciationMethod(), a.getLocation(), a.getStatus(),
            a.getCreatedAt(), a.getUpdatedAt()
        );
    }
}
