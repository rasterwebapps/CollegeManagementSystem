package com.cms.controller;

import com.cms.dto.LabRequest;
import com.cms.dto.LabResponse;
import com.cms.dto.LabSummaryResponse;
import com.cms.service.LabService;
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
@RequestMapping("/api/v1/labs")
public class LabController {

    private final LabService labService;

    public LabController(LabService labService) {
        this.labService = labService;
    }

    @GetMapping
    public ResponseEntity<List<LabResponse>> findAll() {
        return ResponseEntity.ok(labService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LabResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(labService.findById(id));
    }

    @GetMapping("/by-department/{departmentId}")
    public ResponseEntity<List<LabResponse>> findByDepartmentId(
            @PathVariable Long departmentId) {
        return ResponseEntity.ok(labService.findByDepartmentId(departmentId));
    }

    @GetMapping("/summary")
    public ResponseEntity<LabSummaryResponse> getSummary() {
        return ResponseEntity.ok(labService.getSummary());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'LAB_INCHARGE')")
    public ResponseEntity<LabResponse> create(
            @Valid @RequestBody LabRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(labService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'LAB_INCHARGE')")
    public ResponseEntity<LabResponse> update(
            @PathVariable Long id, @Valid @RequestBody LabRequest request) {
        return ResponseEntity.ok(labService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        labService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
