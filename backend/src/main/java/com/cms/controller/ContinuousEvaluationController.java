package com.cms.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.cms.dto.ContinuousEvaluationRequest;
import com.cms.dto.ContinuousEvaluationResponse;
import com.cms.service.ContinuousEvaluationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/continuous-evaluations")
public class ContinuousEvaluationController {

    private final ContinuousEvaluationService continuousEvaluationService;

    public ContinuousEvaluationController(ContinuousEvaluationService continuousEvaluationService) {
        this.continuousEvaluationService = continuousEvaluationService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FACULTY')")
    public ResponseEntity<List<ContinuousEvaluationResponse>> findAll() {
        return ResponseEntity.ok(continuousEvaluationService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FACULTY')")
    public ResponseEntity<ContinuousEvaluationResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(continuousEvaluationService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FACULTY')")
    public ResponseEntity<ContinuousEvaluationResponse> create(@Valid @RequestBody ContinuousEvaluationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(continuousEvaluationService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_FACULTY')")
    public ResponseEntity<ContinuousEvaluationResponse> update(@PathVariable Long id, @Valid @RequestBody ContinuousEvaluationRequest request) {
        return ResponseEntity.ok(continuousEvaluationService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        continuousEvaluationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
