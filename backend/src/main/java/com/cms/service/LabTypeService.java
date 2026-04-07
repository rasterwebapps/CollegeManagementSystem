package com.cms.service;

import com.cms.dto.LabTypeRequest;
import com.cms.dto.LabTypeResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.LabType;
import com.cms.repository.LabTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LabTypeService {

    private final LabTypeRepository labTypeRepository;

    public LabTypeService(LabTypeRepository labTypeRepository) {
        this.labTypeRepository = labTypeRepository;
    }

    @Transactional(readOnly = true)
    public List<LabTypeResponse> findAll() {
        return labTypeRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public LabTypeResponse findById(Long id) {
        LabType labType = labTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LabType", id));
        return toResponse(labType);
    }

    @Transactional
    public LabTypeResponse create(LabTypeRequest request) {
        if (labTypeRepository.existsByName(request.name())) {
            throw new IllegalArgumentException("Lab type with name '" + request.name() + "' already exists");
        }
        LabType labType = new LabType();
        labType.setName(request.name());
        labType.setDescription(request.description());
        LabType saved = labTypeRepository.save(labType);
        return toResponse(saved);
    }

    @Transactional
    public LabTypeResponse update(Long id, LabTypeRequest request) {
        LabType labType = labTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LabType", id));
        if (!labType.getName().equals(request.name()) && labTypeRepository.existsByName(request.name())) {
            throw new IllegalArgumentException("Lab type with name '" + request.name() + "' already exists");
        }
        labType.setName(request.name());
        labType.setDescription(request.description());
        LabType saved = labTypeRepository.save(labType);
        return toResponse(saved);
    }

    @Transactional
    public void delete(Long id) {
        LabType labType = labTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LabType", id));
        labTypeRepository.delete(labType);
    }

    private LabTypeResponse toResponse(LabType entity) {
        return new LabTypeResponse(
                entity.getId(),
                entity.getName(),
                entity.getDescription()
        );
    }
}
