package com.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cms.dto.LabTypeRequest;
import com.cms.dto.LabTypeResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.LabType;
import com.cms.repository.LabTypeRepository;

@Service
public class LabTypeService {

    private final LabTypeRepository labTypeRepository;

    public LabTypeService(LabTypeRepository labTypeRepository) {
        this.labTypeRepository = labTypeRepository;
    }

    public List<LabTypeResponse> findAll() {
        return labTypeRepository.findAll().stream()
            .map(this::toResponse)
            .toList();
    }

    public LabTypeResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    public LabTypeResponse create(LabTypeRequest request) {
        if (labTypeRepository.existsByName(request.name())) {
            throw new IllegalArgumentException("Lab type with name '" + request.name() + "' already exists");
        }
        LabType labType = new LabType();
        labType.setName(request.name());
        labType.setDescription(request.description());
        return toResponse(labTypeRepository.save(labType));
    }

    public LabTypeResponse update(Long id, LabTypeRequest request) {
        LabType labType = getOrThrow(id);
        labTypeRepository.findByName(request.name())
            .filter(lt -> !lt.getId().equals(id))
            .ifPresent(lt -> {
                throw new IllegalArgumentException("Lab type with name '" + request.name() + "' already exists");
            });
        labType.setName(request.name());
        labType.setDescription(request.description());
        return toResponse(labTypeRepository.save(labType));
    }

    public void delete(Long id) {
        if (!labTypeRepository.existsById(id)) {
            throw new ResourceNotFoundException("LabType", id);
        }
        labTypeRepository.deleteById(id);
    }

    private LabType getOrThrow(Long id) {
        return labTypeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("LabType", id));
    }

    private LabTypeResponse toResponse(LabType lt) {
        return new LabTypeResponse(lt.getId(), lt.getName(), lt.getDescription(), lt.getCreatedAt(), lt.getUpdatedAt());
    }
}
