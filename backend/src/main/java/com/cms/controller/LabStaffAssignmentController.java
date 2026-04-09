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

import com.cms.dto.LabStaffAssignmentRequest;
import com.cms.dto.LabStaffAssignmentResponse;
import com.cms.service.LabStaffAssignmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/lab-staff-assignments")
public class LabStaffAssignmentController {

    private final LabStaffAssignmentService labStaffAssignmentService;

    public LabStaffAssignmentController(LabStaffAssignmentService labStaffAssignmentService) {
        this.labStaffAssignmentService = labStaffAssignmentService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LAB_INCHARGE')")
    public ResponseEntity<List<LabStaffAssignmentResponse>> findAll() {
        return ResponseEntity.ok(labStaffAssignmentService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LAB_INCHARGE')")
    public ResponseEntity<LabStaffAssignmentResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(labStaffAssignmentService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LAB_INCHARGE')")
    public ResponseEntity<LabStaffAssignmentResponse> create(
            @Valid @RequestBody LabStaffAssignmentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(labStaffAssignmentService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LAB_INCHARGE')")
    public ResponseEntity<LabStaffAssignmentResponse> update(
            @PathVariable Long id, @Valid @RequestBody LabStaffAssignmentRequest request) {
        return ResponseEntity.ok(labStaffAssignmentService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        labStaffAssignmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
