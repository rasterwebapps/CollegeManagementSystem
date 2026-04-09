package com.cms.controller;

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

import com.cms.dto.ExperimentOutcomeMappingRequest;
import com.cms.dto.ExperimentOutcomeMappingResponse;
import com.cms.service.ExperimentOutcomeMappingService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/experiment-outcome-mappings")
public class ExperimentOutcomeMappingController {

    private final ExperimentOutcomeMappingService mappingService;

    public ExperimentOutcomeMappingController(ExperimentOutcomeMappingService mappingService) {
        this.mappingService = mappingService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FACULTY')")
    public ResponseEntity<List<ExperimentOutcomeMappingResponse>> findAll() {
        return ResponseEntity.ok(mappingService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FACULTY')")
    public ResponseEntity<ExperimentOutcomeMappingResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(mappingService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FACULTY')")
    public ResponseEntity<ExperimentOutcomeMappingResponse> create(
            @Valid @RequestBody ExperimentOutcomeMappingRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mappingService.create(request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        mappingService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
