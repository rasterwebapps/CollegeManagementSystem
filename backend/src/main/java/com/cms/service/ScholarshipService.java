package com.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cms.dto.ScholarshipRequest;
import com.cms.dto.ScholarshipResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.Scholarship;
import com.cms.model.StudentProfile;
import com.cms.repository.ScholarshipRepository;
import com.cms.repository.StudentProfileRepository;

@Service
public class ScholarshipService {

    private final ScholarshipRepository scholarshipRepository;
    private final StudentProfileRepository studentProfileRepository;

    public ScholarshipService(ScholarshipRepository scholarshipRepository,
                              StudentProfileRepository studentProfileRepository) {
        this.scholarshipRepository = scholarshipRepository;
        this.studentProfileRepository = studentProfileRepository;
    }

    public List<ScholarshipResponse> findAll() {
        return scholarshipRepository.findAll().stream().map(this::toResponse).toList();
    }

    public ScholarshipResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    public ScholarshipResponse create(ScholarshipRequest request) {
        StudentProfile student = studentProfileRepository.findById(request.studentId())
            .orElseThrow(() -> new ResourceNotFoundException("StudentProfile", request.studentId()));
        Scholarship s = new Scholarship();
        s.setStudent(student);
        s.setName(request.name());
        s.setAmount(request.amount());
        s.setScholarshipType(request.scholarshipType());
        s.setAwardedDate(request.awardedDate());
        s.setExpiryDate(request.expiryDate());
        s.setStatus(request.status() != null ? request.status() : "ACTIVE");
        s.setRemarks(request.remarks());
        return toResponse(scholarshipRepository.save(s));
    }

    public ScholarshipResponse update(Long id, ScholarshipRequest request) {
        Scholarship s = getOrThrow(id);
        StudentProfile student = studentProfileRepository.findById(request.studentId())
            .orElseThrow(() -> new ResourceNotFoundException("StudentProfile", request.studentId()));
        s.setStudent(student);
        s.setName(request.name());
        s.setAmount(request.amount());
        s.setScholarshipType(request.scholarshipType());
        s.setAwardedDate(request.awardedDate());
        s.setExpiryDate(request.expiryDate());
        if (request.status() != null) { s.setStatus(request.status()); }
        s.setRemarks(request.remarks());
        return toResponse(scholarshipRepository.save(s));
    }

    public void delete(Long id) {
        if (!scholarshipRepository.existsById(id)) {
            throw new ResourceNotFoundException("Scholarship", id);
        }
        scholarshipRepository.deleteById(id);
    }

    private Scholarship getOrThrow(Long id) {
        return scholarshipRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Scholarship", id));
    }

    private ScholarshipResponse toResponse(Scholarship s) {
        return new ScholarshipResponse(
            s.getId(), s.getStudent().getId(),
            s.getStudent().getFirstName() + " " + s.getStudent().getLastName(),
            s.getName(), s.getAmount(), s.getScholarshipType(),
            s.getAwardedDate(), s.getExpiryDate(), s.getStatus(), s.getRemarks(),
            s.getCreatedAt(), s.getUpdatedAt()
        );
    }
}
