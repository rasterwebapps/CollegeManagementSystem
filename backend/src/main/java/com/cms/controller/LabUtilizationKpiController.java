package com.cms.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.cms.dto.LabUtilizationKpiRequest;
import com.cms.dto.LabUtilizationKpiResponse;
import com.cms.service.LabUtilizationKpiService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/lab-utilization-kpis")
public class LabUtilizationKpiController {

    private final LabUtilizationKpiService labUtilizationKpiService;

    public LabUtilizationKpiController(LabUtilizationKpiService labUtilizationKpiService) {
        this.labUtilizationKpiService = labUtilizationKpiService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LAB_INCHARGE')")
    public ResponseEntity<List<LabUtilizationKpiResponse>> findAll() {
        return ResponseEntity.ok(labUtilizationKpiService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LAB_INCHARGE')")
    public ResponseEntity<LabUtilizationKpiResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(labUtilizationKpiService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LAB_INCHARGE')")
    public ResponseEntity<LabUtilizationKpiResponse> create(@Valid @RequestBody LabUtilizationKpiRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(labUtilizationKpiService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LAB_INCHARGE')")
    public ResponseEntity<LabUtilizationKpiResponse> update(@PathVariable Long id, @Valid @RequestBody LabUtilizationKpiRequest request) {
        return ResponseEntity.ok(labUtilizationKpiService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        labUtilizationKpiService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
