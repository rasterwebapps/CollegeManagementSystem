package com.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cms.dto.DepreciationRequest;
import com.cms.dto.DepreciationResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.Asset;
import com.cms.model.Depreciation;
import com.cms.repository.AssetRepository;
import com.cms.repository.DepreciationRepository;

@Service
public class DepreciationService {

    private final DepreciationRepository depreciationRepository;
    private final AssetRepository assetRepository;

    public DepreciationService(DepreciationRepository depreciationRepository,
                               AssetRepository assetRepository) {
        this.depreciationRepository = depreciationRepository;
        this.assetRepository = assetRepository;
    }

    public List<DepreciationResponse> findAll() {
        return depreciationRepository.findAll().stream().map(this::toResponse).toList();
    }

    public DepreciationResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    public DepreciationResponse create(DepreciationRequest request) {
        Asset asset = assetRepository.findById(request.assetId())
            .orElseThrow(() -> new ResourceNotFoundException("Asset", request.assetId()));
        Depreciation d = new Depreciation();
        d.setAsset(asset);
        d.setFiscalYear(request.fiscalYear());
        d.setOpeningValue(request.openingValue());
        d.setDepreciationAmount(request.depreciationAmount());
        d.setClosingValue(request.closingValue());
        d.setCalculatedDate(request.calculatedDate());
        return toResponse(depreciationRepository.save(d));
    }

    public DepreciationResponse update(Long id, DepreciationRequest request) {
        Depreciation d = getOrThrow(id);
        Asset asset = assetRepository.findById(request.assetId())
            .orElseThrow(() -> new ResourceNotFoundException("Asset", request.assetId()));
        d.setAsset(asset);
        d.setFiscalYear(request.fiscalYear());
        d.setOpeningValue(request.openingValue());
        d.setDepreciationAmount(request.depreciationAmount());
        d.setClosingValue(request.closingValue());
        d.setCalculatedDate(request.calculatedDate());
        return toResponse(depreciationRepository.save(d));
    }

    public void delete(Long id) {
        if (!depreciationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Depreciation", id);
        }
        depreciationRepository.deleteById(id);
    }

    private Depreciation getOrThrow(Long id) {
        return depreciationRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Depreciation", id));
    }

    private DepreciationResponse toResponse(Depreciation d) {
        return new DepreciationResponse(
            d.getId(), d.getAsset().getId(), d.getAsset().getName(),
            d.getFiscalYear(), d.getOpeningValue(),
            d.getDepreciationAmount(), d.getClosingValue(),
            d.getCalculatedDate(), d.getCreatedAt(), d.getUpdatedAt()
        );
    }
}
