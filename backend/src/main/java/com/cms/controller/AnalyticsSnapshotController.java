package com.cms.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.cms.dto.AnalyticsSnapshotRequest;
import com.cms.dto.AnalyticsSnapshotResponse;
import com.cms.service.AnalyticsSnapshotService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/analytics-snapshots")
public class AnalyticsSnapshotController {

    private final AnalyticsSnapshotService analyticsSnapshotService;

    public AnalyticsSnapshotController(AnalyticsSnapshotService analyticsSnapshotService) {
        this.analyticsSnapshotService = analyticsSnapshotService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FACULTY')")
    public ResponseEntity<List<AnalyticsSnapshotResponse>> findAll() {
        return ResponseEntity.ok(analyticsSnapshotService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FACULTY')")
    public ResponseEntity<AnalyticsSnapshotResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(analyticsSnapshotService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<AnalyticsSnapshotResponse> create(@Valid @RequestBody AnalyticsSnapshotRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(analyticsSnapshotService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<AnalyticsSnapshotResponse> update(@PathVariable Long id, @Valid @RequestBody AnalyticsSnapshotRequest request) {
        return ResponseEntity.ok(analyticsSnapshotService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        analyticsSnapshotService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
