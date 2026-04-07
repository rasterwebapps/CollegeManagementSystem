package com.cms.controller;

import com.cms.dto.ExperimentOutcomeMappingRequest;
import com.cms.dto.ExperimentOutcomeMappingResponse;
import com.cms.service.ExperimentOutcomeMappingService;
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
@RequestMapping("/api/v1/experiment-outcome-mappings")
public class ExperimentOutcomeMappingController {

    private final ExperimentOutcomeMappingService experimentOutcomeMappingService;

    public ExperimentOutcomeMappingController(
            ExperimentOutcomeMappingService experimentOutcomeMappingService) {
        this.experimentOutcomeMappingService = experimentOutcomeMappingService;
    }

    @GetMapping("/by-experiment/{experimentId}")
    public ResponseEntity<List<ExperimentOutcomeMappingResponse>> findByExperimentId(
            @PathVariable Long experimentId) {
        return ResponseEntity.ok(
                experimentOutcomeMappingService.findByExperimentId(experimentId));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'FACULTY')")
    public ResponseEntity<ExperimentOutcomeMappingResponse> create(
            @Valid @RequestBody ExperimentOutcomeMappingRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(experimentOutcomeMappingService.create(request));
    }

    @DeleteMapping("/{experimentId}/{courseOutcomeId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FACULTY')")
    public ResponseEntity<Void> delete(
            @PathVariable Long experimentId,
            @PathVariable Long courseOutcomeId) {
        experimentOutcomeMappingService.delete(experimentId, courseOutcomeId);
        return ResponseEntity.noContent().build();
    }
}
