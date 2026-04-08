package com.cms.service;

import com.cms.dto.FacultyLabExpertiseRequest;
import com.cms.dto.FacultyLabExpertiseResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.FacultyLabExpertise;
import com.cms.model.FacultyProfile;
import com.cms.model.LabType;
import com.cms.repository.FacultyLabExpertiseRepository;
import com.cms.repository.FacultyProfileRepository;
import com.cms.repository.LabTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Transactional(readOnly = true)
    public List<FacultyLabExpertiseResponse> findByFacultyId(Long facultyId) {
        return expertiseRepository.findByFacultyId(facultyId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public FacultyLabExpertiseResponse findById(Long id) {
        FacultyLabExpertise expertise = expertiseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("FacultyLabExpertise", id));
        return toResponse(expertise);
    }

    @Transactional
    public FacultyLabExpertiseResponse create(FacultyLabExpertiseRequest request) {
        FacultyProfile faculty = facultyProfileRepository.findById(request.facultyId())
                .orElseThrow(() -> new ResourceNotFoundException("FacultyProfile", request.facultyId()));
        LabType labType = labTypeRepository.findById(request.labTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("LabType", request.labTypeId()));

        FacultyLabExpertise expertise = new FacultyLabExpertise();
        expertise.setFaculty(faculty);
        expertise.setLabType(labType);
        expertise.setProficiencyLevel(request.proficiencyLevel());
        expertise.setCertified(request.certified() != null ? request.certified() : false);
        FacultyLabExpertise saved = expertiseRepository.save(expertise);
        return toResponse(saved);
    }

    @Transactional
    public FacultyLabExpertiseResponse update(Long id, FacultyLabExpertiseRequest request) {
        FacultyLabExpertise expertise = expertiseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("FacultyLabExpertise", id));
        FacultyProfile faculty = facultyProfileRepository.findById(request.facultyId())
                .orElseThrow(() -> new ResourceNotFoundException("FacultyProfile", request.facultyId()));
        LabType labType = labTypeRepository.findById(request.labTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("LabType", request.labTypeId()));

        expertise.setFaculty(faculty);
        expertise.setLabType(labType);
        expertise.setProficiencyLevel(request.proficiencyLevel());
        if (request.certified() != null) {
            expertise.setCertified(request.certified());
        }
        FacultyLabExpertise saved = expertiseRepository.save(expertise);
        return toResponse(saved);
    }

    @Transactional
    public void delete(Long id) {
        FacultyLabExpertise expertise = expertiseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("FacultyLabExpertise", id));
        expertiseRepository.delete(expertise);
    }

    private FacultyLabExpertiseResponse toResponse(FacultyLabExpertise entity) {
        return new FacultyLabExpertiseResponse(
                entity.getId(),
                entity.getFaculty().getId(),
                entity.getFaculty().getEmployeeId(),
                entity.getLabType().getId(),
                entity.getLabType().getName(),
                entity.getProficiencyLevel(),
                entity.isCertified()
        );
    }
}
