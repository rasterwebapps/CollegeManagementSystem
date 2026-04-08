package com.cms.controller;

import com.cms.dto.ExperimentRequest;
import com.cms.dto.ExperimentResponse;
import com.cms.service.ExperimentService;
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
@RequestMapping("/api/v1/experiments")
public class ExperimentController {

    private final ExperimentService experimentService;

    public ExperimentController(ExperimentService experimentService) {
        this.experimentService = experimentService;
    }

    @GetMapping("/by-course/{courseId}")
    public ResponseEntity<List<ExperimentResponse>> findByCourseId(
            @PathVariable Long courseId) {
        return ResponseEntity.ok(experimentService.findByCourseId(courseId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExperimentResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(experimentService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'FACULTY')")
    public ResponseEntity<ExperimentResponse> create(
            @Valid @RequestBody ExperimentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(experimentService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FACULTY')")
    public ResponseEntity<ExperimentResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody ExperimentRequest request) {
        return ResponseEntity.ok(experimentService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FACULTY')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        experimentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
