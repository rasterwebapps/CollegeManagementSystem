package com.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cms.dto.FeeStructureRequest;
import com.cms.dto.FeeStructureResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.FeeStructure;
import com.cms.model.Program;
import com.cms.model.Semester;
import com.cms.repository.FeeStructureRepository;
import com.cms.repository.ProgramRepository;
import com.cms.repository.SemesterRepository;

@Service
public class FeeStructureService {

    private final FeeStructureRepository feeStructureRepository;
    private final ProgramRepository programRepository;
    private final SemesterRepository semesterRepository;

    public FeeStructureService(FeeStructureRepository feeStructureRepository,
                               ProgramRepository programRepository,
                               SemesterRepository semesterRepository) {
        this.feeStructureRepository = feeStructureRepository;
        this.programRepository = programRepository;
        this.semesterRepository = semesterRepository;
    }

    public List<FeeStructureResponse> findAll() {
        return feeStructureRepository.findAll().stream().map(this::toResponse).toList();
    }

    public FeeStructureResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    public FeeStructureResponse create(FeeStructureRequest request) {
        Program program = programRepository.findById(request.programId())
            .orElseThrow(() -> new ResourceNotFoundException("Program", request.programId()));
        Semester semester = semesterRepository.findById(request.semesterId())
            .orElseThrow(() -> new ResourceNotFoundException("Semester", request.semesterId()));
        FeeStructure fs = new FeeStructure();
        fs.setProgram(program);
        fs.setSemester(semester);
        fs.setFeeType(request.feeType());
        fs.setAmount(request.amount());
        fs.setCurrency(request.currency() != null ? request.currency() : "INR");
        fs.setEffectiveFrom(request.effectiveFrom());
        fs.setEffectiveTo(request.effectiveTo());
        fs.setStatus(request.status() != null ? request.status() : "ACTIVE");
        return toResponse(feeStructureRepository.save(fs));
    }

    public FeeStructureResponse update(Long id, FeeStructureRequest request) {
        FeeStructure fs = getOrThrow(id);
        Program program = programRepository.findById(request.programId())
            .orElseThrow(() -> new ResourceNotFoundException("Program", request.programId()));
        Semester semester = semesterRepository.findById(request.semesterId())
            .orElseThrow(() -> new ResourceNotFoundException("Semester", request.semesterId()));
        fs.setProgram(program);
        fs.setSemester(semester);
        fs.setFeeType(request.feeType());
        fs.setAmount(request.amount());
        if (request.currency() != null) { fs.setCurrency(request.currency()); }
        fs.setEffectiveFrom(request.effectiveFrom());
        fs.setEffectiveTo(request.effectiveTo());
        if (request.status() != null) { fs.setStatus(request.status()); }
        return toResponse(feeStructureRepository.save(fs));
    }

    public void delete(Long id) {
        if (!feeStructureRepository.existsById(id)) {
            throw new ResourceNotFoundException("FeeStructure", id);
        }
        feeStructureRepository.deleteById(id);
    }

    private FeeStructure getOrThrow(Long id) {
        return feeStructureRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("FeeStructure", id));
    }

    private FeeStructureResponse toResponse(FeeStructure fs) {
        return new FeeStructureResponse(
            fs.getId(), fs.getProgram().getId(), fs.getProgram().getName(),
            fs.getSemester().getId(), fs.getSemester().getName(),
            fs.getFeeType(), fs.getAmount(), fs.getCurrency(),
            fs.getEffectiveFrom(), fs.getEffectiveTo(), fs.getStatus(),
            fs.getCreatedAt(), fs.getUpdatedAt()
        );
    }
}
