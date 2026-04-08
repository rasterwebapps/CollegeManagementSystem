package com.cms.controller;

import com.cms.dto.LabStaffAssignmentRequest;
import com.cms.dto.LabStaffAssignmentResponse;
import com.cms.service.LabStaffAssignmentService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/lab-staff")
public class LabStaffAssignmentController {

    private final LabStaffAssignmentService labStaffAssignmentService;

    public LabStaffAssignmentController(LabStaffAssignmentService labStaffAssignmentService) {
        this.labStaffAssignmentService = labStaffAssignmentService;
    }

    @GetMapping("/by-lab/{labId}")
    public ResponseEntity<List<LabStaffAssignmentResponse>> findByLabId(
            @PathVariable Long labId) {
        return ResponseEntity.ok(labStaffAssignmentService.findByLabId(labId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LabStaffAssignmentResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(labStaffAssignmentService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'LAB_INCHARGE')")
    public ResponseEntity<LabStaffAssignmentResponse> create(
            @Valid @RequestBody LabStaffAssignmentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(labStaffAssignmentService.create(request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'LAB_INCHARGE')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        labStaffAssignmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
