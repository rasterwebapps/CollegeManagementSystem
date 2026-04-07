package com.cms.service;

import com.cms.dto.ProgramRequest;
import com.cms.dto.ProgramResponse;
import com.cms.exception.ResourceNotFoundException;
import com.cms.model.Department;
import com.cms.model.Program;
import com.cms.repository.DepartmentRepository;
import com.cms.repository.ProgramRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProgramService {

    private final ProgramRepository programRepository;
    private final DepartmentRepository departmentRepository;

    public ProgramService(ProgramRepository programRepository,
                          DepartmentRepository departmentRepository) {
        this.programRepository = programRepository;
        this.departmentRepository = departmentRepository;
    }

    @Transactional(readOnly = true)
    public List<ProgramResponse> findAll() {
        return programRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProgramResponse findById(Long id) {
        Program program = programRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Program", id));
        return toResponse(program);
    }

    @Transactional(readOnly = true)
    public List<ProgramResponse> findByDepartmentId(Long departmentId) {
        return programRepository.findByDepartmentId(departmentId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
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
        program.setDegreeType(request.degreeType());
        program.setDurationYears(request.durationYears());
        program.setTotalCredits(request.totalCredits());
        program.setActive(request.active() != null ? request.active() : true);
        Program saved = programRepository.save(program);
        return toResponse(saved);
    }

    @Transactional
    public ProgramResponse update(Long id, ProgramRequest request) {
        Program program = programRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Program", id));
        Department department = departmentRepository.findById(request.departmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department", request.departmentId()));

        program.setName(request.name());
        program.setCode(request.code());
        program.setDepartment(department);
        program.setDegreeType(request.degreeType());
        program.setDurationYears(request.durationYears());
        program.setTotalCredits(request.totalCredits());
        if (request.active() != null) {
            program.setActive(request.active());
        }
        Program saved = programRepository.save(program);
        return toResponse(saved);
    }

    @Transactional
    public void delete(Long id) {
        Program program = programRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Program", id));
        programRepository.delete(program);
    }

    private ProgramResponse toResponse(Program entity) {
        return new ProgramResponse(
                entity.getId(),
                entity.getName(),
                entity.getCode(),
                entity.getDepartment().getId(),
                entity.getDepartment().getName(),
                entity.getDegreeType(),
                entity.getDurationYears(),
                entity.getTotalCredits(),
                entity.isActive()
        );
    }
}
