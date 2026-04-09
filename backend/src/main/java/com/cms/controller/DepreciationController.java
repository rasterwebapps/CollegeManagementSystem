package com.cms.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.cms.dto.DepreciationRequest;
import com.cms.dto.DepreciationResponse;
import com.cms.service.DepreciationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/depreciations")
public class DepreciationController {

    private final DepreciationService depreciationService;

    public DepreciationController(DepreciationService depreciationService) {
        this.depreciationService = depreciationService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LAB_INCHARGE')")
    public ResponseEntity<List<DepreciationResponse>> findAll() {
        return ResponseEntity.ok(depreciationService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LAB_INCHARGE')")
    public ResponseEntity<DepreciationResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(depreciationService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<DepreciationResponse> create(@Valid @RequestBody DepreciationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(depreciationService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<DepreciationResponse> update(@PathVariable Long id, @Valid @RequestBody DepreciationRequest request) {
        return ResponseEntity.ok(depreciationService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        depreciationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
