package com.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cms.dto.FacultyLabExpertiseRequest;
import com.cms.dto.FacultyLabExpertiseResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.FacultyLabExpertise;
import com.cms.model.FacultyProfile;
import com.cms.model.LabType;
import com.cms.repository.FacultyLabExpertiseRepository;
import com.cms.repository.FacultyProfileRepository;
import com.cms.repository.LabTypeRepository;

@Service
public class FacultyLabExpertiseService {

    private final FacultyLabExpertiseRepository expertiseRepository;
    private final FacultyProfileRepository facultyProfileRepository;
    private final LabTypeRepository labTypeRepository;

    public FacultyLabExpertiseService(FacultyLabExpertiseRepository expertiseRepository,
                                      FacultyProfileRepository facultyProfileRepository,
                                      LabTypeRepository labTypeRepository) {
        this.expertiseRepository = expertiseRepository;
        this.facultyProfileRepository = facultyProfileRepository;
        this.labTypeRepository = labTypeRepository;
    }

    public List<FacultyLabExpertiseResponse> findAll() {
        return expertiseRepository.findAll().stream()
            .map(this::toResponse)
            .toList();
    }

    public FacultyLabExpertiseResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    public FacultyLabExpertiseResponse create(FacultyLabExpertiseRequest request) {
        FacultyProfile faculty = facultyProfileRepository.findById(request.facultyId())
            .orElseThrow(() -> new ResourceNotFoundException("FacultyProfile", request.facultyId()));
        LabType labType = labTypeRepository.findById(request.labTypeId())
            .orElseThrow(() -> new ResourceNotFoundException("LabType", request.labTypeId()));
        FacultyLabExpertise expertise = new FacultyLabExpertise();
        expertise.setFaculty(faculty);
        expertise.setLabType(labType);
        expertise.setProficiencyLevel(request.proficiencyLevel());
        expertise.setCertifiedDate(request.certifiedDate());
        return toResponse(expertiseRepository.save(expertise));
    }

    public FacultyLabExpertiseResponse update(Long id, FacultyLabExpertiseRequest request) {
        FacultyLabExpertise expertise = getOrThrow(id);
        FacultyProfile faculty = facultyProfileRepository.findById(request.facultyId())
            .orElseThrow(() -> new ResourceNotFoundException("FacultyProfile", request.facultyId()));
        LabType labType = labTypeRepository.findById(request.labTypeId())
            .orElseThrow(() -> new ResourceNotFoundException("LabType", request.labTypeId()));
        expertise.setFaculty(faculty);
        expertise.setLabType(labType);
        expertise.setProficiencyLevel(request.proficiencyLevel());
        expertise.setCertifiedDate(request.certifiedDate());
        return toResponse(expertiseRepository.save(expertise));
    }

    public void delete(Long id) {
        if (!expertiseRepository.existsById(id)) {
            throw new ResourceNotFoundException("FacultyLabExpertise", id);
        }
        expertiseRepository.deleteById(id);
    }

    private FacultyLabExpertise getOrThrow(Long id) {
        return expertiseRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("FacultyLabExpertise", id));
    }

    private FacultyLabExpertiseResponse toResponse(FacultyLabExpertise e) {
        return new FacultyLabExpertiseResponse(
            e.getId(), e.getFaculty().getId(),
            e.getFaculty().getFirstName() + " " + e.getFaculty().getLastName(),
            e.getLabType().getId(), e.getLabType().getName(),
            e.getProficiencyLevel(), e.getCertifiedDate(),
            e.getCreatedAt(), e.getUpdatedAt()
        );
    }
}
