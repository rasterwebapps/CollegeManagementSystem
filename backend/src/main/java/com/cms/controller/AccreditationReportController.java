package com.cms.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.cms.dto.AccreditationReportRequest;
import com.cms.dto.AccreditationReportResponse;
import com.cms.service.AccreditationReportService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/accreditation-reports")
public class AccreditationReportController {

    private final AccreditationReportService accreditationReportService;

    public AccreditationReportController(AccreditationReportService accreditationReportService) {
        this.accreditationReportService = accreditationReportService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FACULTY')")
    public ResponseEntity<List<AccreditationReportResponse>> findAll() {
        return ResponseEntity.ok(accreditationReportService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FACULTY')")
    public ResponseEntity<AccreditationReportResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(accreditationReportService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<AccreditationReportResponse> create(@Valid @RequestBody AccreditationReportRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(accreditationReportService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<AccreditationReportResponse> update(@PathVariable Long id, @Valid @RequestBody AccreditationReportRequest request) {
        return ResponseEntity.ok(accreditationReportService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        accreditationReportService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
