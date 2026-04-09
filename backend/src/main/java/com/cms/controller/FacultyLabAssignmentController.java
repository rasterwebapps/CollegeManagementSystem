package com.cms.controller;

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

import com.cms.dto.FacultyLabAssignmentRequest;
import com.cms.dto.FacultyLabAssignmentResponse;
import com.cms.service.FacultyLabAssignmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/faculty-lab-assignments")
public class FacultyLabAssignmentController {

    private final FacultyLabAssignmentService facultyLabAssignmentService;

    public FacultyLabAssignmentController(FacultyLabAssignmentService facultyLabAssignmentService) {
        this.facultyLabAssignmentService = facultyLabAssignmentService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FACULTY')")
    public ResponseEntity<List<FacultyLabAssignmentResponse>> findAll() {
        return ResponseEntity.ok(facultyLabAssignmentService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FACULTY')")
    public ResponseEntity<FacultyLabAssignmentResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(facultyLabAssignmentService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<FacultyLabAssignmentResponse> create(
            @Valid @RequestBody FacultyLabAssignmentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(facultyLabAssignmentService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<FacultyLabAssignmentResponse> update(
            @PathVariable Long id, @Valid @RequestBody FacultyLabAssignmentRequest request) {
        return ResponseEntity.ok(facultyLabAssignmentService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        facultyLabAssignmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
