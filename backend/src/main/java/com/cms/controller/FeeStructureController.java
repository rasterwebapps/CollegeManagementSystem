package com.cms.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.cms.dto.FeeStructureRequest;
import com.cms.dto.FeeStructureResponse;
import com.cms.service.FeeStructureService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/fee-structures")
public class FeeStructureController {

    private final FeeStructureService feeStructureService;

    public FeeStructureController(FeeStructureService feeStructureService) {
        this.feeStructureService = feeStructureService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FACULTY')")
    public ResponseEntity<List<FeeStructureResponse>> findAll() {
        return ResponseEntity.ok(feeStructureService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FACULTY')")
    public ResponseEntity<FeeStructureResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(feeStructureService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<FeeStructureResponse> create(@Valid @RequestBody FeeStructureRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(feeStructureService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<FeeStructureResponse> update(@PathVariable Long id, @Valid @RequestBody FeeStructureRequest request) {
        return ResponseEntity.ok(feeStructureService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        feeStructureService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
