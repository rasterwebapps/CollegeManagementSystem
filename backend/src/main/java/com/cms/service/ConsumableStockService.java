package com.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cms.dto.ConsumableStockRequest;
import com.cms.dto.ConsumableStockResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.ConsumableStock;
import com.cms.model.Lab;
import com.cms.repository.ConsumableStockRepository;
import com.cms.repository.LabRepository;

@Service
public class ConsumableStockService {

    private final ConsumableStockRepository consumableStockRepository;
    private final LabRepository labRepository;

    public ConsumableStockService(ConsumableStockRepository consumableStockRepository,
                                  LabRepository labRepository) {
        this.consumableStockRepository = consumableStockRepository;
        this.labRepository = labRepository;
    }

    public List<ConsumableStockResponse> findAll() {
        return consumableStockRepository.findAll().stream().map(this::toResponse).toList();
    }

    public ConsumableStockResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    public ConsumableStockResponse create(ConsumableStockRequest request) {
        Lab lab = labRepository.findById(request.labId())
            .orElseThrow(() -> new ResourceNotFoundException("Lab", request.labId()));
        ConsumableStock cs = new ConsumableStock();
        cs.setLab(lab);
        cs.setItemName(request.itemName());
        cs.setQuantity(request.quantity());
        cs.setUnit(request.unit());
        cs.setMinimumThreshold(request.minimumThreshold() != null ? request.minimumThreshold() : 0);
        cs.setUnitCost(request.unitCost());
        cs.setLastRestocked(request.lastRestocked());
        cs.setStatus(request.status() != null ? request.status() : "IN_STOCK");
        return toResponse(consumableStockRepository.save(cs));
    }

    public ConsumableStockResponse update(Long id, ConsumableStockRequest request) {
        ConsumableStock cs = getOrThrow(id);
        Lab lab = labRepository.findById(request.labId())
            .orElseThrow(() -> new ResourceNotFoundException("Lab", request.labId()));
        cs.setLab(lab);
        cs.setItemName(request.itemName());
        cs.setQuantity(request.quantity());
        cs.setUnit(request.unit());
        if (request.minimumThreshold() != null) { cs.setMinimumThreshold(request.minimumThreshold()); }
        cs.setUnitCost(request.unitCost());
        cs.setLastRestocked(request.lastRestocked());
        if (request.status() != null) { cs.setStatus(request.status()); }
        return toResponse(consumableStockRepository.save(cs));
    }

    public void delete(Long id) {
        if (!consumableStockRepository.existsById(id)) {
            throw new ResourceNotFoundException("ConsumableStock", id);
        }
        consumableStockRepository.deleteById(id);
    }

    private ConsumableStock getOrThrow(Long id) {
        return consumableStockRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("ConsumableStock", id));
    }

    private ConsumableStockResponse toResponse(ConsumableStock cs) {
        return new ConsumableStockResponse(
            cs.getId(), cs.getLab().getId(), cs.getLab().getName(),
            cs.getItemName(), cs.getQuantity(), cs.getUnit(),
            cs.getMinimumThreshold(), cs.getUnitCost(), cs.getLastRestocked(),
            cs.getStatus(), cs.getCreatedAt(), cs.getUpdatedAt()
        );
    }
}
