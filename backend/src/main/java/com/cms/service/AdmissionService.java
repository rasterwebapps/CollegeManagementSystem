package com.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cms.dto.AdmissionRequest;
import com.cms.dto.AdmissionResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.Admission;
import com.cms.model.Program;
import com.cms.model.StudentProfile;
import com.cms.repository.AdmissionRepository;
import com.cms.repository.ProgramRepository;
import com.cms.repository.StudentProfileRepository;

@Service
public class AdmissionService {

    private final AdmissionRepository admissionRepository;
    private final StudentProfileRepository studentProfileRepository;
    private final ProgramRepository programRepository;

    public AdmissionService(AdmissionRepository admissionRepository,
                            StudentProfileRepository studentProfileRepository,
                            ProgramRepository programRepository) {
        this.admissionRepository = admissionRepository;
        this.studentProfileRepository = studentProfileRepository;
        this.programRepository = programRepository;
    }

    public List<AdmissionResponse> findAll() {
        return admissionRepository.findAll().stream()
            .map(this::toResponse)
            .toList();
    }

    public AdmissionResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    public AdmissionResponse create(AdmissionRequest request) {
        if (admissionRepository.existsByApplicationNumber(request.applicationNumber())) {
            throw new IllegalStateException("Application number already exists: " + request.applicationNumber());
        }
        StudentProfile student = studentProfileRepository.findById(request.studentId())
            .orElseThrow(() -> new ResourceNotFoundException("StudentProfile", request.studentId()));
        Program program = programRepository.findById(request.programId())
            .orElseThrow(() -> new ResourceNotFoundException("Program", request.programId()));

        Admission admission = new Admission();
        mapRequestToEntity(admission, request, student, program);
        admission.setStatus(request.status() != null ? request.status() : "PENDING");
        return toResponse(admissionRepository.save(admission));
    }

    public AdmissionResponse update(Long id, AdmissionRequest request) {
        Admission admission = getOrThrow(id);
        admissionRepository.findByApplicationNumber(request.applicationNumber())
            .filter(existing -> !existing.getId().equals(id))
            .ifPresent(existing -> {
                throw new IllegalStateException("Application number already exists: " + request.applicationNumber());
            });
        StudentProfile student = studentProfileRepository.findById(request.studentId())
            .orElseThrow(() -> new ResourceNotFoundException("StudentProfile", request.studentId()));
        Program program = programRepository.findById(request.programId())
            .orElseThrow(() -> new ResourceNotFoundException("Program", request.programId()));

        mapRequestToEntity(admission, request, student, program);
        if (request.status() != null) {
            admission.setStatus(request.status());
        }
        return toResponse(admissionRepository.save(admission));
    }

    public void delete(Long id) {
        if (!admissionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Admission", id);
        }
        admissionRepository.deleteById(id);
    }

    private void mapRequestToEntity(Admission admission, AdmissionRequest request,
                                    StudentProfile student, Program program) {
        admission.setStudent(student);
        admission.setApplicationNumber(request.applicationNumber());
        admission.setProgram(program);
        admission.setAdmissionDate(request.admissionDate());
        admission.setAdmissionType(request.admissionType());
        admission.setRemarks(request.remarks());
    }

    private Admission getOrThrow(Long id) {
        return admissionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Admission", id));
    }

    private AdmissionResponse toResponse(Admission a) {
        return new AdmissionResponse(
            a.getId(),
            a.getStudent().getId(),
            a.getStudent().getFirstName() + " " + a.getStudent().getLastName(),
            a.getApplicationNumber(),
            a.getProgram().getId(), a.getProgram().getName(),
            a.getAdmissionDate(), a.getAdmissionType(),
            a.getStatus(), a.getRemarks(),
            a.getCreatedAt(), a.getUpdatedAt()
        );
    }
}
