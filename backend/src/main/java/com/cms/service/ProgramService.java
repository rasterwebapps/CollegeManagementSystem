package com.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cms.dto.ProgramRequest;
import com.cms.dto.ProgramResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.Department;
import com.cms.model.Program;
import com.cms.repository.DepartmentRepository;
import com.cms.repository.ProgramRepository;

@Service
public class ProgramService {

    private final ProgramRepository programRepository;
    private final DepartmentRepository departmentRepository;

    public ProgramService(ProgramRepository programRepository, DepartmentRepository departmentRepository) {
        this.programRepository = programRepository;
        this.departmentRepository = departmentRepository;
    }

    public List<ProgramResponse> findAll() {
        return programRepository.findAll().stream()
            .map(this::toResponse)
            .toList();
    }

    public ProgramResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    public ProgramResponse create(ProgramRequest request) {
        if (programRepository.existsByCode(request.code())) {
            throw new IllegalArgumentException("Program with code '" + request.code() + "' already exists");
        }
        Department department = departmentRepository.findById(request.departmentId())
            .orElseThrow(() -> new ResourceNotFoundException("Department", request.departmentId()));
        Program program = new Program();
        program.setName(request.name());
        program.setCode(request.code());
        program.setDepartment(department);
        program.setDurationYears(request.durationYears());
        program.setDegreeType(request.degreeType());
        return toResponse(programRepository.save(program));
    }

    public ProgramResponse update(Long id, ProgramRequest request) {
        Program program = getOrThrow(id);
        programRepository.findByCode(request.code())
            .filter(p -> !p.getId().equals(id))
            .ifPresent(p -> {
                throw new IllegalArgumentException("Program with code '" + request.code() + "' already exists");
            });
        Department department = departmentRepository.findById(request.departmentId())
            .orElseThrow(() -> new ResourceNotFoundException("Department", request.departmentId()));
        program.setName(request.name());
        program.setCode(request.code());
        program.setDepartment(department);
        program.setDurationYears(request.durationYears());
        program.setDegreeType(request.degreeType());
        return toResponse(programRepository.save(program));
    }

    public void delete(Long id) {
        if (!programRepository.existsById(id)) {
            throw new ResourceNotFoundException("Program", id);
        }
        programRepository.deleteById(id);
    }

    private Program getOrThrow(Long id) {
        return programRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Program", id));
    }

    private ProgramResponse toResponse(Program p) {
        return new ProgramResponse(
            p.getId(), p.getName(), p.getCode(),
            p.getDepartment().getId(), p.getDepartment().getName(),
            p.getDurationYears(), p.getDegreeType(),
            p.getCreatedAt(), p.getUpdatedAt()
        );
    }
}
