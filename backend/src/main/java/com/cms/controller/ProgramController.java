package com.cms.controller;

import com.cms.dto.ProgramRequest;
import com.cms.dto.ProgramResponse;
import com.cms.service.ProgramService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/programs")
public class ProgramController {

    private final ProgramService programService;

    public ProgramController(ProgramService programService) {
        this.programService = programService;
    }

    @GetMapping
    public ResponseEntity<List<ProgramResponse>> findAll() {
        return ResponseEntity.ok(programService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProgramResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(programService.findById(id));
    }

    @GetMapping("/by-department/{departmentId}")
    public ResponseEntity<List<ProgramResponse>> findByDepartmentId(
            @PathVariable Long departmentId) {
        return ResponseEntity.ok(programService.findByDepartmentId(departmentId));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProgramResponse> create(
            @Valid @RequestBody ProgramRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(programService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProgramResponse> update(
            @PathVariable Long id, @Valid @RequestBody ProgramRequest request) {
        return ResponseEntity.ok(programService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        programService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
