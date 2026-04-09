package com.cms.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.cms.dto.LabFeeRequest;
import com.cms.dto.LabFeeResponse;
import com.cms.service.LabFeeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/lab-fees")
public class LabFeeController {

    private final LabFeeService labFeeService;

    public LabFeeController(LabFeeService labFeeService) {
        this.labFeeService = labFeeService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LAB_INCHARGE')")
    public ResponseEntity<List<LabFeeResponse>> findAll() {
        return ResponseEntity.ok(labFeeService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_LAB_INCHARGE')")
    public ResponseEntity<LabFeeResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(labFeeService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<LabFeeResponse> create(@Valid @RequestBody LabFeeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(labFeeService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<LabFeeResponse> update(@PathVariable Long id, @Valid @RequestBody LabFeeRequest request) {
        return ResponseEntity.ok(labFeeService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        labFeeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
